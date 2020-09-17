package com.example.gameapp.ui.genreGrid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.gameapp.domain.Game
import com.example.gameapp.domain.GameDataSource
import com.example.gameapp.domain.GameDataSourceFactory
import com.example.gameapp.network.GameApiBody
import com.example.gameapp.repository.GameApiStatus
import java.lang.Appendable

class GameGridViewModel(application: Application) : AndroidViewModel(application) {
    var apiStatus : LiveData<GameApiStatus>
    var gamePagedList: LiveData<PagedList<Game>>
    private val applicationRef = application
    init {
        val gameDataSourceFactory = GameDataSourceFactory(null,application)
        apiStatus = gameDataSourceFactory.gameDataSource.gameApiStatus
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(GameDataSource.pageSize)
            .build()
        gamePagedList = LivePagedListBuilder(gameDataSourceFactory, pagedListConfig)
            .build()
    }

    fun changeGenre(genreString: GameApiBody.GenreString) {
        val gameDataSourceFactory = GameDataSourceFactory(genreString,applicationRef)
        apiStatus = gameDataSourceFactory.gameDataSource.gameApiStatus
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(GameDataSource.pageSize)
            .build()
        gamePagedList = LivePagedListBuilder(gameDataSourceFactory, pagedListConfig)
            .build()
    }
}