package com.arkantos.arkantos.repository

import android.app.Application
import android.util.Log
import com.arkantos.arkantos.database.GameDatabaseModel
import com.arkantos.arkantos.database.GamesDatabase
import com.arkantos.arkantos.domain.Game
import com.arkantos.arkantos.network.BackendApi
import com.arkantos.arkantos.network.models.UserNetworkModel
import com.arkantos.arkantos.network.models.asFavoriteGamesNetworkModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BackendRepository(application: Application) {
    private val database = GamesDatabase.getInstance(application)

    suspend fun login(user: UserNetworkModel) {
        withContext(Dispatchers.IO) {
            try {
                BackendApi.retrofitService.login(user)
            } catch (t: Throwable) {
                handleError(t)
            }
        }
    }

    suspend fun syncFavoriteGames() {
        withContext(Dispatchers.IO) {
            try {
                val favoriteGames = database.gamesDao.getFavoriteGamesSync()
                Log.i("BackendRepository", favoriteGames.asFavoriteGamesNetworkModel().toString())
                BackendApi.retrofitService.postFavoriteGames(favoriteGames.asFavoriteGamesNetworkModel())
            } catch (t: Throwable) {
                handleError(t)
            }
        }
    }


    private fun handleError(t: Throwable) {
        Log.i("BackendRepository", t.message ?: "error with null message")
    }
}