package com.arkantos.arkantos.ui.favoriteGames

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.arkantos.arkantos.domain.asDomainModel
import com.arkantos.arkantos.repository.GameRepository

class FavoriteGamesViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application)
    val apiStatus = gameRepository.apiStatus
    var favoriteGamesList = Transformations.map(gameRepository.favoriteGames) {
        Log.i("favorite", it.size.toString())
        it.asDomainModel()
    }

}