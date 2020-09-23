package com.arkantos.arkantos.network

import com.arkantos.arkantos.network.models.FavoriteGamesNetworkModel
import com.arkantos.arkantos.network.models.UserNetworkModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BackendApiService {
    @POST("auth/login")
    suspend fun login(@Body user: UserNetworkModel)
    
    @GET("games/favoriteGames/{userToken}")
    suspend fun getFavoriteGames(@Path("userToken") userToken: String): FavoriteGamesNetworkModel

    @POST("games/favoriteGames")
    suspend fun postFavoriteGames(@Body favoriteGames: FavoriteGamesNetworkModel)
}

object BackendApi {
    val retrofitService: BackendApiService by lazy {
        RetrofitHolder.getBackendApiServiceRetrofit().create(BackendApiService::class.java)
    }
}