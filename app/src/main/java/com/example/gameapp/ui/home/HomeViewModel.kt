package com.example.gameapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.gameapp.domain.Game
import com.example.gameapp.domain.GameDataSource
import com.example.gameapp.domain.GameDataSourceFactory
import com.example.gameapp.repository.GameApiStatus

class HomeViewModel : ViewModel() {
    val actionGamesApiStatus: LiveData<GameApiStatus>
    val actionGamesPagedList: LiveData<PagedList<Game>>

    val adventureGamesApiStatus: LiveData<GameApiStatus>
    var adventureGamesPagedList: LiveData<PagedList<Game>>

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(GameDataSource.pageSize)
            .build()

        val actionGameDataSourceFactory = GameDataSourceFactory()
        actionGamesApiStatus = actionGameDataSourceFactory.gameDataSource.gameApiStatus
        actionGamesPagedList = LivePagedListBuilder(actionGameDataSourceFactory, pagedListConfig)
            .build()

        val adventureGameDataSourceFactory = GameDataSourceFactory()
        adventureGamesApiStatus = adventureGameDataSourceFactory.gameDataSource.gameApiStatus
        adventureGamesPagedList = LivePagedListBuilder(adventureGameDataSourceFactory, pagedListConfig)
            .build()

    }


}