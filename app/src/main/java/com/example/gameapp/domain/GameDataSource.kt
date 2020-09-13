package com.example.gameapp.domain

import androidx.paging.PageKeyedDataSource
import com.example.gameapp.network.GameApiBody
import com.example.gameapp.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameDataSource(private val genre: GameApiBody.GenreString?) : PageKeyedDataSource<Int, Game>() {
    private val job = Job()
    private val dataSourceScope = CoroutineScope(job + Dispatchers.Main)
    private val gameRepository = GameRepository()

    companion object {
        const val pageSize = 48
        const val firstPage = 0
    }

    val gameApiStatus = gameRepository.apiStatus

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Game>
    ) {
        dataSourceScope.launch {

            val body = GameApiBody(limit = pageSize, offset = firstPage)
            if(genre != null)
                body.addGenre(genre)
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
            val body = GameApiBody(limit = pageSize, offset = params.key * pageSize)
            if(genre != null)
                body.addGenre(genre)
            gameRepository.refreshGames(body)
            val gameList = gameRepository.allGames.value
            var nextKey: Int? = params.key + 1
            if ((params.key + 1) * pageSize > 5000) nextKey = null
            if(gameList != null)
                 callback.onResult(gameList, nextKey)
            else
                callback.onResult(listOf(), null)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Game>) {
//        dataSourceScope.launch {
//            val body = GameApiBody(limit = pageSize, offset = params.key * pageSize).getBodyString()
//            val gameList = GameApi.retrofitService.getGames(body)
//            var nextKey: Int? = params.key - 1
//            if (params.key == 1) nextKey = null
//            callback.onResult(gameList.asDomainModel(), nextKey)
//        }
    }


}