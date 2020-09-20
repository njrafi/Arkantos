package com.arkantos.arkantos.domain

import android.app.Application
import androidx.paging.DataSource
import com.arkantos.arkantos.network.GameApiBody

class GameDataSourceFactory(
    genre: GameApiBody.GenreString? = null,
    application: Application
) : DataSource.Factory<Int, Game>() {
    //    private val _gameDataSourceLiveData = MutableLiveData<PageKeyedDataSource<Int,Game>>()
//    val gameDateSourceLiveData: LiveData<PageKeyedDataSource<Int, Game>>
//        get() = _gameDataSourceLiveData
    var gameDataSource = GameDataSource(genre,application)
    override fun create(): DataSource<Int, Game> {
        //_gameDataSourceLiveData.postValue(gameDataSource)
        return gameDataSource
    }
}