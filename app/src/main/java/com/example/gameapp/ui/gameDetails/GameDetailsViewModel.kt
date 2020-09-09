package com.example.gameapp.ui.gameDetails

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gameapp.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameDetailsViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val gameRepository = GameRepository()

    val apiStatus = gameRepository.apiStatus
    val game = gameRepository.singleGame

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