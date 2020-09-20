package com.arkantos.arkantos.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arkantos.arkantos.database.FavoriteGameDatabaseModel
import com.arkantos.arkantos.database.GamesDatabase
import com.arkantos.arkantos.database.PopularGameDatabaseModel
import com.arkantos.arkantos.domain.Game
import com.arkantos.arkantos.domain.GameDataSource
import com.arkantos.arkantos.domain.asDomainModel
import com.arkantos.arkantos.network.GameApi
import com.arkantos.arkantos.network.GameApiBody
import com.arkantos.arkantos.network.asDatabaseModel
import com.arkantos.arkantos.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

enum class GameApiStatus {
    LOADING, ERROR, DONE
}

class GameRepository(private val application: Application) {

    private val _allGames = MutableLiveData<List<Game>>()
    val allGames: LiveData<List<Game>>
        get() = _allGames

    private val _singleGame = MutableLiveData<Game>()
    val singleGame: LiveData<Game>
        get() = _singleGame

    private val _apiStatus = MutableLiveData<GameApiStatus>()
    val apiStatus: LiveData<GameApiStatus>
        get() = _apiStatus

    private val database = GamesDatabase.getInstance(application)
    val favoriteGames = database.gamesDao.getFavoriteGames()

    private suspend fun refreshGames(gameApiBody: GameApiBody = GameApiBody()) {
        withContext(Dispatchers.IO) {
            try {
                _apiStatus.postValue(GameApiStatus.LOADING)
                val gameList = GameApi.retrofitService.getGames(gameApiBody.getBodyString())
                Log.i("GameRepository", gameList.size.toString())
                Log.i("GameRepository", "Inserting to database")
                database.gamesDao.insert(gameList.asDatabaseModel())
                Log.i("GameRepository", "Inserting to database finished")
                _allGames.postValue(gameList.asDomainModel())
                _apiStatus.postValue(GameApiStatus.DONE)
            } catch (t: Throwable) {
                handleError(t)
            }
        }
    }

    suspend fun getPopularGames(limit: Int = 15) {
        withContext(Dispatchers.IO) {
            try {
                Log.i("GameRepository", "Read for popular games From database Started")
                val popularGamesFromDatabase = database.gamesDao.getPopularGames()
                Log.i("GameRepository", "Read for popular games From database Finished")
                Log.i("GameRepository", popularGamesFromDatabase.size.toString())

                if (popularGamesFromDatabase.size >= limit) {
                    _allGames.postValue(popularGamesFromDatabase.asDomainModel())
                } else {
                    val gameApiBody = GameApiBody(
                        limit = limit,
                        sortParameter = GameApiBody.SortParameters.Popularity,
                        whereConditions = "aggregated_rating != null & cover.image_id != null & aggregated_rating >= 93"
                    )
                    Log.i("GameRepository", "Retrieve From Network Started")
                    val gamesFromNetwork =
                        GameApi.retrofitService.getGames(gameApiBody.getBodyString())
                    Log.i("GameRepository", "Retrieve From Network Finished")
                    Log.i("GameRepository", gamesFromNetwork.size.toString())
                    Log.i("GameRepository", "Inserting to database")
                    database.gamesDao.insert(gamesFromNetwork.asDatabaseModel())
                    Log.i("GameRepository", "Inserting to database finished")
                    val popularGamesDatabaseModel = gamesFromNetwork.map {
                        PopularGameDatabaseModel(it.id)
                    }
                    Log.i("GameRepository", "Inserting to database popular games")
                    database.gamesDao.insertPopularGames(popularGamesDatabaseModel)
                    Log.i("GameRepository", "Inserting to database popular games finished")

                    _allGames.postValue(gamesFromNetwork.asDomainModel())

                }
            } catch (t: Throwable) {
                handleError(t)
            }
        }
    }

    suspend fun getGamesByGenre(
        genre: GameApiBody.GenreString?,
        limit: Int = GameDataSource.pageSize,
        offset: Int = 0
    ) {
        withContext(Dispatchers.IO) {
            try {
                _apiStatus.postValue(GameApiStatus.LOADING)
                Log.i("GameRepository", "Read From database Started")
                val gamesFromDatabase =
                    database.gamesDao.getGamesByGenre(genre?.stringValue ?: "", limit, offset)
                Log.i("GameRepository", "Read From database finished")
                Log.i("GameRepository", gamesFromDatabase.size.toString())
                if (gamesFromDatabase.size >= limit) {
                    Log.i("GameRepository", "Found all from database")
                    _allGames.postValue(gamesFromDatabase.asDomainModel())
                    _apiStatus.postValue(GameApiStatus.DONE)
                } else {
                    val gameApiBody = GameApiBody(limit = limit, offset = offset)
                    gameApiBody.addGenre(genre)
                    Log.i("GameRepository", "Retrieve From Network Started")
                    val gamesFromNetwork =
                        GameApi.retrofitService.getGames(gameApiBody.getBodyString())
                    Log.i("GameRepository", "Retrieve From Network Finished")
                    Log.i("GameRepository", gamesFromNetwork.size.toString())
                    Log.i("GameRepository", "Inserting to database")
                    database.gamesDao.insert(gamesFromNetwork.asDatabaseModel())
                    Log.i("GameRepository", "Inserting to database finished")
                    val gamesFromDatabaseAfterNetworkCall =
                        database.gamesDao.getGamesByGenre(genre?.stringValue ?: "", limit, offset)
                    Log.i("GameRepository", "Read From database After network call finished")
                    Log.i("GameRepository", gamesFromDatabaseAfterNetworkCall.size.toString())
                    _allGames.postValue(gamesFromDatabaseAfterNetworkCall.asDomainModel())
                    _apiStatus.postValue(GameApiStatus.DONE)
                }
            } catch (t: Throwable) {
                handleError(t)
            }
        }
    }

    suspend fun getGameById(id: Long) {
        withContext(Dispatchers.IO) {
            try {
                _apiStatus.postValue(GameApiStatus.LOADING)
                val gameList = database.gamesDao.getGameById(id).asDomainModel()
                if (gameList.isNotEmpty()) {
                    _singleGame.postValue(gameList[0])
                    _apiStatus.postValue(GameApiStatus.DONE)
                } else {
                    _apiStatus.postValue(GameApiStatus.ERROR)
                }
            } catch (t: Throwable) {
                handleError(t)
            }
        }
    }

    suspend fun addToFavorites(gameId: Long) {
        withContext(Dispatchers.IO) {
            try {
                database.gamesDao.insertFavoriteGame(FavoriteGameDatabaseModel(gameId))
            } catch (t: Throwable) {
                handleError(t)
            }
        }
    }

    suspend fun removeFromFavorites(gameId: Long) {
        withContext(Dispatchers.IO) {
            try {
                database.gamesDao.deleteFavoriteGame(FavoriteGameDatabaseModel(gameId))
            } catch (t: Throwable) {
                handleError(t)
            }
        }
    }

    private fun handleError(t: Throwable) {
        _apiStatus.postValue(GameApiStatus.ERROR)
        if (t.message != null)
            Log.i("GameRepository", t.message!!)
        else
            Log.i("GameRepository", "error with null message")
    }


}