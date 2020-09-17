package com.example.gameapp.ui.genreGrid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.gameapp.domain.Game
import com.example.gameapp.domain.GameDataSource
import com.example.gameapp.domain.GameDataSourceFactory
import com.example.gameapp.network.GameApiBody
import com.example.gameapp.repository.GameApiStatus
import com.example.gameapp.ui.gameDetails.GameDetailsViewModel
import java.lang.Appendable

class GameGridViewModel(application: Application, private val genreString: GameApiBody.GenreString?) : AndroidViewModel(application) {
    var apiStatus : LiveData<GameApiStatus>
    var gamePagedList: LiveData<PagedList<Game>>
    private val applicationRef = application
    init {
        val gameDataSourceFactory = GameDataSourceFactory(genreString,application)
        apiStatus = gameDataSourceFactory.gameDataSource.gameApiStatus
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(GameDataSource.pageSize)
            .build()
        gamePagedList = LivePagedListBuilder(gameDataSourceFactory, pagedListConfig)
            .build()
    }
    /**
     * Factory for constructing GameDetailViewModel with parameter
     */
    class Factory(val app: Application,private val genreString: GameApiBody.GenreString?) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GameGridViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GameGridViewModel(app, genreString) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}