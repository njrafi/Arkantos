package com.example.gameapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gameapp.domain.Game
import com.example.gameapp.network.GameApi
import com.example.gameapp.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepository {

    private val _allGames = MutableLiveData<List<Game>>()
    val allGames: LiveData<List<Game>>
        get() = _allGames

    suspend fun refreshGames() {
        withContext(Dispatchers.IO) {
            try {
                val gameList = GameApi.retrofitService.getGames()
                Log.i("GameRepository", gameList.size.toString())
                _allGames.postValue(gameList.asDomainModel())
                for (game in gameList.asDomainModel()) {
                    game.name?.let { Log.i("GameRepository", it) }
                    game.coverImageUrl?.let { Log.i("GameRepository", it) }
                }
            } catch (t : Throwable) {
                if(t.message != null)
                    Log.i("GameRepository", t.message!!)
                else
                    Log.i("GameRepository", "error with null message")
            }
        }
    }


}