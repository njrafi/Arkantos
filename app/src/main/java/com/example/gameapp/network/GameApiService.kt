package com.example.gameapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://api-v3.igdb.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface GameApiService {
    @GET("games")
    suspend fun getGames(): List<GameNetworkModel>
}

object GameApi {
    val retrofitService: GameApiService by lazy {
        retrofit.create(GameApiService::class.java)
    }
}