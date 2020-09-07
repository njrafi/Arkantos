package com.example.gameapp.network

import android.net.Uri
import com.example.gameapp.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.net.URI


private const val BASE_URL = "https://api-v3.igdb.com/"
private const val USER_KEY = BuildConfig.IGDB_USER_KEY
private const val bodyString = "where cover.image_id != null"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

const val fields: String = "name, summary, cover.image_id"
const val limit: Int = 500
const val offset: Int = 0
const val whereConditions: String = "cover.image_id != null"

interface GameApiService {
    @Headers(
        "user-key: $USER_KEY",
    )
    @POST("games")
    suspend fun getGames(
        @Body body: String = "fields $fields;" +
                "limit $limit;" +
                "offset $offset;" +
                "where $whereConditions;"
    ):
            List<GameNetworkModel>
}

object GameApi {

    val retrofitService: GameApiService by lazy {
        retrofit.create(GameApiService::class.java)
    }
}