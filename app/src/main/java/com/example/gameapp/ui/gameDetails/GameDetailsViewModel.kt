package com.example.gameapp.ui.gameDetails

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gameapp.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class GameDetailsViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val gameRepository = GameRepository()

    val apiStatus = gameRepository.apiStatus
    val game = gameRepository.singleGame

    val rating = Transformations.map(game) {
        if(it.rating == null) {
            "Rating: N/A"
        } else {
            "Rating: ${it.rating}%"
        }
    }

    val releaseDate = Transformations.map(game) {
        var dateString = ""
        if(it.releaseDate != null) {
            val simpleDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale("en"))
            val date = Date(it.releaseDate * 1000)
            dateString = simpleDateFormat.format(date)
        }
        "Release Date: $dateString"
    }

    val genres = Transformations.map(game) {
        var genresList = ""
        it.genres?.let { genres ->
            for(genre in genres) {
                if (genresList.isNotEmpty()) genresList += ", "
                genresList += genre
            }
        }
        "Genres: $genresList"
    }

    val platforms = Transformations.map(game) {
        var platformsList = ""
        it.platforms?.let { platforms ->
            for(platform in platforms) {
                if (platformsList.isNotEmpty()) platformsList += ", "
                platformsList += platform
            }
        }
        "Platforms: $platformsList"
    }

    val summaryShouldBeShown = Transformations.map(game) {
        it?.summary != null
    }
    val storylineShouldBeShown = Transformations.map(game) {
        it?.storyline != null
    }

    fun getSpecificGame(id: Long) {
        viewModelScope.launch {
            gameRepository.getGameById(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}