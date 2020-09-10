package com.example.gameapp.ui.genreGrid

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.gameapp.domain.Game
import com.example.gameapp.domain.GameDataSource
import com.example.gameapp.domain.GameDataSourceFactory
import com.example.gameapp.repository.GameApiStatus

class GameGridViewModel : ViewModel() {
    val apiStatus : LiveData<GameApiStatus>
    var gamePagedList: LiveData<PagedList<Game>>
    private var liveDataSource: LiveData<PageKeyedDataSource<Int, Game>>
    init {
        val gameDataSourceFactory = GameDataSourceFactory()
        liveDataSource = gameDataSourceFactory.gameDateSourceLiveData
        apiStatus = gameDataSourceFactory.gameDataSource.gameApiStatus
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(GameDataSource.pageSize)
            .build()
        gamePagedList = LivePagedListBuilder(gameDataSourceFactory, pagedListConfig)
            .build()
    }
}