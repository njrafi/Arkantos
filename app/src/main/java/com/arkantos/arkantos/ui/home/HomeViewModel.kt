package com.arkantos.arkantos.ui.home

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arkantos.arkantos.domain.Game
import com.arkantos.arkantos.domain.GameDataSource
import com.arkantos.arkantos.domain.GameDataSourceFactory
import com.arkantos.arkantos.network.GameApiBody
import com.arkantos.arkantos.network.asNetworkModel
import com.arkantos.arkantos.repository.BackendRepository
import com.arkantos.arkantos.repository.GameApiStatus
import com.arkantos.arkantos.repository.GameRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)
    private val gameRepository = GameRepository(application)
    private val backendRepository = BackendRepository(application)
    var totalApiCalls = -1

    private val adventureGamesApiStatus: LiveData<GameApiStatus>
    val adventureGamesPagedList: LiveData<PagedList<Game>>

    private val rpgGamesApiStatus: LiveData<GameApiStatus>
    var rpgGamesPagedList: LiveData<PagedList<Game>>

    private val rtsGamesApiStatus: LiveData<GameApiStatus>
    var rtsGamesPagedList: LiveData<PagedList<Game>>

    private val shooterGamesApiStatus: LiveData<GameApiStatus>
    var shooterGamesPagedList: LiveData<PagedList<Game>>

    private val fightingGamesApiStatus: LiveData<GameApiStatus>
    var fightingGamesPagedList: LiveData<PagedList<Game>>

    private val racingGamesApiStatus: LiveData<GameApiStatus>
    var racingGamesPagedList: LiveData<PagedList<Game>>

    val popularGames = gameRepository.allGames

    val allGamesLoaded = MediatorLiveData<Int>()

    init {
        allGamesLoaded.value = 0
        viewModelScope.launch {
            gameRepository.getPopularGames()
        }
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(GameDataSource.pageSize)
            .setPrefetchDistance(GameDataSource.pageSize / 2)
            .setInitialLoadSizeHint(GameDataSource.pageSize / 2)
            .build()

        // TODO: Refactor?
        val adventureGamesDataSourceFactory =
            GameDataSourceFactory(GameApiBody.GenreString.Adventure, application)
        adventureGamesApiStatus = adventureGamesDataSourceFactory.gameDataSource.gameApiStatus
        adventureGamesPagedList =
            LivePagedListBuilder(adventureGamesDataSourceFactory, pagedListConfig)
                .build()

        val rpgGamesDataSourceFactory =
            GameDataSourceFactory(GameApiBody.GenreString.RolePlaying, application)
        rpgGamesApiStatus = rpgGamesDataSourceFactory.gameDataSource.gameApiStatus
        rpgGamesPagedList = LivePagedListBuilder(rpgGamesDataSourceFactory, pagedListConfig)
            .build()

        val rtsGamesDataSourceFactory =
            GameDataSourceFactory(GameApiBody.GenreString.RealTimeStrategy, application)
        rtsGamesApiStatus = rtsGamesDataSourceFactory.gameDataSource.gameApiStatus
        rtsGamesPagedList = LivePagedListBuilder(rtsGamesDataSourceFactory, pagedListConfig)
            .build()

        val shooterGamesDataSourceFactory =
            GameDataSourceFactory(GameApiBody.GenreString.Shooter, application)
        shooterGamesApiStatus = shooterGamesDataSourceFactory.gameDataSource.gameApiStatus
        shooterGamesPagedList = LivePagedListBuilder(shooterGamesDataSourceFactory, pagedListConfig)
            .build()

        val racingGamesDataSourceFactory =
            GameDataSourceFactory(GameApiBody.GenreString.Racing, application)
        racingGamesApiStatus = racingGamesDataSourceFactory.gameDataSource.gameApiStatus
        racingGamesPagedList = LivePagedListBuilder(racingGamesDataSourceFactory, pagedListConfig)
            .build()

        val fightingGamesDataSourceFactory =
            GameDataSourceFactory(GameApiBody.GenreString.Fighting, application)
        fightingGamesApiStatus = fightingGamesDataSourceFactory.gameDataSource.gameApiStatus
        fightingGamesPagedList =
            LivePagedListBuilder(fightingGamesDataSourceFactory, pagedListConfig)
                .build()

        addSources()
    }

    fun loginFinished() {
        viewModelScope.launch {
            Firebase.auth.currentUser?.asNetworkModel()?.let { backendRepository.login(it) }
        }
    }


    private fun addSources() {
        // Remember to change
        totalApiCalls = 7
        allGamesLoaded.addSource(popularGames) {
            if (it.isNotEmpty()) {
                allGamesLoaded.value = allGamesLoaded.value?.plus(1)
                allGamesLoaded.removeSource(popularGames)
            }
        }
        allGamesLoaded.addSource(adventureGamesApiStatus) {
            if (it == GameApiStatus.DONE) {
                allGamesLoaded.value = allGamesLoaded.value?.plus(1)
                allGamesLoaded.removeSource(adventureGamesApiStatus)
            }
        }
        allGamesLoaded.addSource(rpgGamesApiStatus) {
            if (it == GameApiStatus.DONE) {
                allGamesLoaded.value = allGamesLoaded.value?.plus(1)
                allGamesLoaded.removeSource(rpgGamesApiStatus)
            }
        }

        allGamesLoaded.addSource(rtsGamesApiStatus) {
            if (it == GameApiStatus.DONE) {
                allGamesLoaded.value = allGamesLoaded.value?.plus(1)
                allGamesLoaded.removeSource(rtsGamesApiStatus)
            }
        }

        allGamesLoaded.addSource(shooterGamesApiStatus) {
            if (it == GameApiStatus.DONE) {
                allGamesLoaded.value = allGamesLoaded.value?.plus(1)
                allGamesLoaded.removeSource(shooterGamesApiStatus)
            }
        }

        allGamesLoaded.addSource(fightingGamesApiStatus) {
            if (it == GameApiStatus.DONE) {
                allGamesLoaded.value = allGamesLoaded.value?.plus(1)
                allGamesLoaded.removeSource(fightingGamesApiStatus)
            }
        }

        allGamesLoaded.addSource(racingGamesApiStatus) {
            if (it == GameApiStatus.DONE) {
                allGamesLoaded.value = allGamesLoaded.value?.plus(1)
                allGamesLoaded.removeSource(racingGamesApiStatus)
            }
        }

    }


}