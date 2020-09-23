package com.arkantos.arkantos.network

import com.arkantos.arkantos.BuildConfig
import com.arkantos.arkantos.network.models.GameNetworkModel
import retrofit2.http.*

private const val USER_KEY = BuildConfig.IGDB_USER_KEY

interface GameApiService {
    @Headers(
        "user-key: $USER_KEY",
    )
    @POST("games")
    suspend fun getGames(
        @Body body: String = GameApiBody().getBodyString()
    ):
            List<GameNetworkModel>
}

object GameApi {

    val retrofitService: GameApiService by lazy {
        RetrofitHolder.getMovieApiServiceRetrofit().create(GameApiService::class.java)
    }
}