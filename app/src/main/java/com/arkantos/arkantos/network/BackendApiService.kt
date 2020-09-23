package com.arkantos.arkantos.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BackendApiService {
    @POST("auth/login")
    suspend fun login(@Body user: UserNetworkModel)
}

object BackendApi {
    val retrofitService: BackendApiService by lazy {
        RetrofitHolder.getBackendApiServiceRetrofit().create(BackendApiService::class.java)
    }
}