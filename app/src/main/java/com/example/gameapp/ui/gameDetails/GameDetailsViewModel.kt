package com.example.gameapp.ui.gameDetails

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.example.gameapp.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class GameDetailsViewModel(application: Application, val gameId: Long) :
    AndroidViewModel(application) {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val gameRepository = GameRepository(application)

    val apiStatus = gameRepository.apiStatus
    val game = gameRepository.singleGame

    val favoriteGames = gameRepository.favoriteGames

    val rating = Transformations.map(game) {
        if (it.rating == null) {
            "Rating: N/A"
        } else {
            "Rating: ${it.rating}%"
        }
    }

    val releaseDate = Transformations.map(game) {
        var dateString = "N/A"
        if (it.releaseDate != null) {
            val simpleDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale("en"))
            val date = Date(it.releaseDate * 1000)
            dateString = simpleDateFormat.format(date)
        }
        "Release Date: $dateString"
    }

    val genres = Transformations.map(game) {
        var genresList = ""
        it.genres?.let { genres ->
            for (genre in genres) {
                if (genresList.isNotEmpty()) genresList += ", "
                genresList += genre
            }
        }
        if(genresList.isEmpty()) genresList = "N/A"
        "Genres: $genresList"
    }

    val platforms = Transformations.map(game) {
        var platformsList = ""
        it.platforms?.let { platforms ->
            for (platform in platforms) {
                if (platformsList.isNotEmpty()) platformsList += ", "
                platformsList += platform
            }
        }
        if(platformsList.isEmpty()) platformsList = "N/A"
        "Platforms: $platformsList"
    }

    val summaryShouldBeShown = Transformations.map(game) {
        it?.summary != null
    }
    val storylineShouldBeShown = Transformations.map(game) {
        it?.storyline != null
    }

    init {
        viewModelScope.launch {
            gameRepository.getGameById(gameId)
        }
    }

    fun addToFavorite() {
        viewModelScope.launch {
            gameRepository.addToFavorites(gameId)
        }

    }

    fun removeFromFavourite() {
        viewModelScope.launch {
            gameRepository.removeFromFavorites(gameId)
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing GameDetailViewModel with parameter
     */
    class Factory(val app: Application, val gameId: Long) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GameDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GameDetailsViewModel(app, gameId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}