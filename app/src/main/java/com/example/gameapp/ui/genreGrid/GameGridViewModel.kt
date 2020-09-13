package com.example.gameapp.ui.genreGrid

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

class GameGridViewModel() : ViewModel() {
    var apiStatus : LiveData<GameApiStatus>
    var gamePagedList: LiveData<PagedList<Game>>
    init {
        val gameDataSourceFactory = GameDataSourceFactory()
        apiStatus = gameDataSourceFactory.gameDataSource.gameApiStatus
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(GameDataSource.pageSize)
            .build()
        gamePagedList = LivePagedListBuilder(gameDataSourceFactory, pagedListConfig)
            .build()
    }

    fun changeGenre(genreString: GameApiBody.GenreString) {
        val gameDataSourceFactory = GameDataSourceFactory(genreString)
        apiStatus = gameDataSourceFactory.gameDataSource.gameApiStatus
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(GameDataSource.pageSize)
            .build()
        gamePagedList = LivePagedListBuilder(gameDataSourceFactory, pagedListConfig)
            .build()
    }
}