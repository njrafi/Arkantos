package com.arkantos.arkantos.repository

import android.app.Application
import android.util.Log
import com.arkantos.arkantos.database.FavoriteGameDatabaseModel
import com.arkantos.arkantos.database.GameDatabaseModel
import com.arkantos.arkantos.database.GamesDatabase
import com.arkantos.arkantos.domain.Game
import com.arkantos.arkantos.network.BackendApi
import com.arkantos.arkantos.network.models.UserNetworkModel
import com.arkantos.arkantos.network.models.asFavoriteGamesNetworkModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    suspend fun syncFavoriteGamesWithServer() {
        withContext(Dispatchers.IO) {
            try {
                val favoriteGames = database.gamesDao.getFavoriteGamesSync()
                BackendApi.retrofitService.postFavoriteGames(favoriteGames.asFavoriteGamesNetworkModel())
            } catch (t: Throwable) {
                handleError(t)
            }
        }
    }

    suspend fun getFavoriteGamesFromServer() {
        withContext(Dispatchers.IO) {
            try {3
                val userToken = Firebase.auth.currentUser?.uid
                if (userToken != null) {
                    val favoriteGames = BackendApi.retrofitService.getFavoriteGames(userToken)
                    database.gamesDao.insert(favoriteGames.favoriteGames)
                    database.gamesDao.insertFavoriteGame(favoriteGames.favoriteGames.map {
                        FavoriteGameDatabaseModel(it.id)
                    })
                } else {
                    throw Throwable("UserToken Found null in getFavoriteGames")
                }
            } catch (t: Throwable) {
                handleError(t)
            }
        }
    }


    private fun handleError(t: Throwable) {
        Log.i("BackendRepository", t.message ?: "error with null message")
    }
}