package com.arkantos.arkantos.repository

import android.app.Application
import android.util.Log
import com.arkantos.arkantos.network.BackendApi
import com.arkantos.arkantos.network.UserNetworkModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BackendRepository(application: Application) {

    suspend fun login(user: UserNetworkModel) {
        withContext(Dispatchers.IO) {
            try {
                BackendApi.retrofitService.login(user)
            } catch (t: Throwable) {
                handleError(t)
            }
        }
    }


    private fun handleError(t: Throwable) {
        Log.i("BackendRepository", t.message ?: "error with null message")
    }
}