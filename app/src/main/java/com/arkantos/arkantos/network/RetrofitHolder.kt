package com.arkantos.arkantos.network

import com.arkantos.arkantos.BuildConfig
import com.arkantos.arkantos.network.RetrofitHolder.BACKEND_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitHolder {
    private const val MOVIE_API_URL = "https://api-v3.igdb.com/"
    private const val BACKEND_URL  = BuildConfig.BACKEND_URL
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val moshiConverterFactory = MoshiConverterFactory.create(moshi)
    private val scalarsConverterFactory = ScalarsConverterFactory.create()
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .build()

    private var movieApiServiceRetrofit: Retrofit? = null
    fun getMovieApiServiceRetrofit(): Retrofit {
        if (movieApiServiceRetrofit == null) {
            movieApiServiceRetrofit = buildRetrofit(MOVIE_API_URL)
        }
        return movieApiServiceRetrofit as Retrofit
    }

    private var backendServiceRetrofit: Retrofit? = null
    fun getBackendApiServiceRetrofit(): Retrofit {
        if (backendServiceRetrofit == null) {
            backendServiceRetrofit = buildRetrofit(BACKEND_URL)
        }
        return backendServiceRetrofit as Retrofit
    }

    private fun buildRetrofit(baseUrl: String): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(baseUrl)
            .client(httpClient)
            .build()
    }
}