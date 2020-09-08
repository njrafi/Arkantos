package com.example.gameapp.ui.gameDetails

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

    fun getSpecificGame(id: String) {
        viewModelScope.launch {
            gameRepository.getGameById(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}