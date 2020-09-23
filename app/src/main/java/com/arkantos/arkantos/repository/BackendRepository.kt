package com.arkantos.arkantos.repository

import android.app.Application
import android.util.Log
import com.arkantos.arkantos.network.BackendApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BackendRepository(application: Application) {
    init {

    }

    suspend fun getRootForTest() {
        withContext(Dispatchers.IO) {
            BackendApi.retrofitService.getRootForTest()
        }
    }
    private fun handleError(t: Throwable) {
        Log.i("BackendRepository", t.message ?: "error with null message")
    }
}