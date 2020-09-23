package com.arkantos.arkantos.network

import com.arkantos.arkantos.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitHolder {
    private const val MOVIE_API_URL = "https://api-v3.igdb.com/"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val scalarsConverterFactory = ScalarsConverterFactory.create()
    private val moshiConverterFactory = MoshiConverterFactory.create(moshi)

    private var movieApiServiceRetrofit: Retrofit? = null

    fun getMovieApiServiceRetrofit() : Retrofit {
        if(movieApiServiceRetrofit == null) {
            movieApiServiceRetrofit = Retrofit.Builder()
                .addConverterFactory(scalarsConverterFactory)
                .addConverterFactory(moshiConverterFactory)
                .baseUrl(MOVIE_API_URL)
                .build()
        }
        return movieApiServiceRetrofit as Retrofit
    }
}