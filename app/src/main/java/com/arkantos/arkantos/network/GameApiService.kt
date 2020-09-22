package com.arkantos.arkantos.network

import com.arkantos.arkantos.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*


private const val BASE_URL = "https://api-v3.igdb.com/"
private const val USER_KEY = BuildConfig.IGDB_USER_KEY
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

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
        retrofit.create(GameApiService::class.java)
    }
}