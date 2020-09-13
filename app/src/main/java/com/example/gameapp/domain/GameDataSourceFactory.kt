package com.example.gameapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource

class GameDataSourceFactory(): DataSource.Factory<Int,Game>() {
//    private val _gameDataSourceLiveData = MutableLiveData<PageKeyedDataSource<Int,Game>>()
//    val gameDateSourceLiveData: LiveData<PageKeyedDataSource<Int, Game>>
//        get() = _gameDataSourceLiveData
    var gameDataSource = GameDataSource()
    override fun create(): DataSource<Int, Game> {
        //_gameDataSourceLiveData.postValue(gameDataSource)
        return gameDataSource
    }
}