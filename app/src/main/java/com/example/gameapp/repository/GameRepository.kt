package com.example.gameapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gameapp.domain.Game
import com.example.gameapp.network.GameApi
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

    private val _apiStatus = MutableLiveData<GameApiStatus>()
    val apiStatus: LiveData<GameApiStatus>
        get() = _apiStatus

    init {
        _apiStatus.value = GameApiStatus.LOADING
    }

    suspend fun refreshGames() {
        withContext(Dispatchers.IO) {
            try {
                Log.i("GameRepository", "GameApiStatus: Loading")
                val gameList = GameApi.retrofitService.getGames()
                Log.i("GameRepository", gameList.size.toString())
                _allGames.postValue(gameList.asDomainModel())
                _apiStatus.postValue(GameApiStatus.DONE)
                Log.i("GameRepository", "GameApiStatus: Done")
                for (game in gameList.asDomainModel()) {
                    game.name?.let { Log.i("GameRepository", it) }
                    game.coverImageUrl?.let { Log.i("GameRepository", it) }
                }
            } catch (t : Throwable) {
                Log.i("GameRepository", "GameApiStatus: Error")
                _apiStatus.postValue(GameApiStatus.ERROR)
                if(t.message != null)
                    Log.i("GameRepository", t.message!!)
                else
                    Log.i("GameRepository", "error with null message")
            }
        }
    }


}