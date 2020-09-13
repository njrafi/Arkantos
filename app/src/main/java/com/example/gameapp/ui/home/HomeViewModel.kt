package com.example.gameapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.gameapp.domain.Game
import com.example.gameapp.domain.GameDataSource
import com.example.gameapp.domain.GameDataSourceFactory
import com.example.gameapp.network.GameApiBody
import com.example.gameapp.repository.GameApiStatus

class HomeViewModel : ViewModel() {
    val adventureGamesApiStatus: LiveData<GameApiStatus>
    val adventureGamesPagedList: LiveData<PagedList<Game>>

    val rpgGamesApiStatus: LiveData<GameApiStatus>
    var rpgGamesPagedList: LiveData<PagedList<Game>>

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(GameDataSource.pageSize)
            .build()

        val adventureGamesDataSourceFactory = GameDataSourceFactory(GameApiBody.GenreString.Adventure)
        adventureGamesApiStatus = adventureGamesDataSourceFactory.gameDataSource.gameApiStatus
        adventureGamesPagedList = LivePagedListBuilder(adventureGamesDataSourceFactory, pagedListConfig)
            .build()

        val rpgGamesDataSourceFactory = GameDataSourceFactory(GameApiBody.GenreString.RolePlaying)
        rpgGamesApiStatus = rpgGamesDataSourceFactory.gameDataSource.gameApiStatus
        rpgGamesPagedList = LivePagedListBuilder(rpgGamesDataSourceFactory, pagedListConfig)
            .build()

    }


}