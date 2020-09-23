package com.arkantos.arkantos.network

import retrofit2.http.Body
import retrofit2.http.GET

interface BackendApiService {

    @GET("/")
    suspend fun getRootForTest()
}

object BackendApi {
    val retrofitService: BackendApiService by lazy {
        RetrofitHolder.getBackendApiServiceRetrofit().create(BackendApiService::class.java)
    }
}