package com.example.gameapp.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import com.example.gameapp.network.GameApi
import com.example.gameapp.network.GameApiBody
import com.example.gameapp.network.asDomainModel
import com.example.gameapp.repository.GameApiStatus
import com.example.gameapp.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameDataSource : PageKeyedDataSource<Int, Game>() {
    private val job = Job()
    private val dataSourceScope = CoroutineScope(job + Dispatchers.Main)
    private val gameRepository = GameRepository()

    companion object {
        const val pageSize = 20
        const val firstPage = 0
    }

    val gameApiStatus = gameRepository.apiStatus

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Game>
    ) {
        dataSourceScope.launch {

            val body = GameApiBody(limit = pageSize, offset = firstPage)
            gameRepository.refreshGames(body)
            val gameList = gameRepository.allGames.value
            if(gameList != null)
                callback.onResult(gameList, null, firstPage + 1)
            else
                callback.onResult(listOf(),null,null)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Game>) {
        dataSourceScope.launch {
            val body = GameApiBody(limit = pageSize, offset = params.key * pageSize).getBodyString()
            val gameList = GameApi.retrofitService.getGames(body)
            var nextKey: Int? = params.key + 1
            if ((params.key + 1) * pageSize > 5000) nextKey = null
            callback.onResult(gameList.asDomainModel(), nextKey)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Game>) {
        dataSourceScope.launch {
            val body = GameApiBody(limit = pageSize, offset = params.key * pageSize).getBodyString()
            val gameList = GameApi.retrofitService.getGames(body)
            var nextKey: Int? = params.key - 1
            if (params.key == 1) nextKey = null
            callback.onResult(gameList.asDomainModel(), nextKey)
        }
    }


}