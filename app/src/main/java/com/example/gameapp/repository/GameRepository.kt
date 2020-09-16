package com.example.gameapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gameapp.database.GamesDatabase
import com.example.gameapp.domain.Game
import com.example.gameapp.domain.GameDataSource
import com.example.gameapp.domain.asDomainModel
import com.example.gameapp.network.GameApi
import com.example.gameapp.network.GameApiBody
import com.example.gameapp.network.asDatabaseModel
import com.example.gameapp.network.asDomainModel
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
        val gameApiBody = GameApiBody(
            limit = limit,
            sortParameter = GameApiBody.SortParameters.Popularity,
            whereConditions = "aggregated_rating != null & cover.image_id != null & aggregated_rating >= 93"
        )
        refreshGames(gameApiBody)
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


    private fun handleError(t: Throwable) {
        _apiStatus.postValue(GameApiStatus.ERROR)
        if (t.message != null)
            Log.i("GameRepository", t.message!!)
        else
            Log.i("GameRepository", "error with null message")
    }


}