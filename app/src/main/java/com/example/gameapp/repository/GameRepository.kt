package com.example.gameapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gameapp.domain.Game
import com.example.gameapp.network.GameApi
import com.example.gameapp.network.GameApiBody
import com.example.gameapp.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

enum class GameApiStatus {
    LOADING, ERROR, DONE
}

class GameRepository {

    private val _allGames = MutableLiveData<List<Game>>()
    val allGames: LiveData<List<Game>>
        get() = _allGames

    private val _singleGame = MutableLiveData<Game>()
    val singleGame: LiveData<Game>
        get() = _singleGame

    private val _apiStatus = MutableLiveData<GameApiStatus>()
    val apiStatus: LiveData<GameApiStatus>
        get() = _apiStatus

    suspend fun refreshGames(gameApiBody: GameApiBody = GameApiBody()) {
        withContext(Dispatchers.IO) {
            try {
                _apiStatus.postValue(GameApiStatus.LOADING)
                val gameList = GameApi.retrofitService.getGames(gameApiBody.getBodyString())
                Log.i("GameRepository", gameList.size.toString())
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

    suspend fun getGamesByGenre(genre: GameApiBody.GenreString) {
        val gameApiBody = GameApiBody()
        gameApiBody.addGenre(genre)
        refreshGames(gameApiBody)
    }

    suspend fun getGameById(id: Long) {
        val fields =
            "name, summary, cover.image_id, storyline, rating, first_release_date, genres.name, platforms.name"
        val whereConditions = "id = $id"
        val body = GameApiBody(fields = fields, whereConditions = whereConditions).getBodyString()
        Log.i("GameRepository", body)
        withContext(Dispatchers.IO) {
            try {
                _apiStatus.postValue(GameApiStatus.LOADING)
                val gameList = GameApi.retrofitService.getGames(body).asDomainModel()
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