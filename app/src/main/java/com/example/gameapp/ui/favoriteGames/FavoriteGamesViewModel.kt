package com.example.gameapp.ui.favoriteGames

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.gameapp.domain.asDomainModel
import com.example.gameapp.repository.GameApiStatus
import com.example.gameapp.repository.GameRepository

class FavoriteGamesViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application)
    val apiStatus = gameRepository.apiStatus
    var favoriteGamesList = Transformations.map(gameRepository.favoriteGames) {
        Log.i("favorite", it.size.toString())
        it.asDomainModel()
    }

}