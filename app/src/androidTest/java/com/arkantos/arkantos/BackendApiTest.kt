package com.arkantos.arkantos

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkantos.arkantos.network.BackendApi
import com.arkantos.arkantos.network.models.UserNetworkModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BackendApiTest {

    private val testUser = UserNetworkModel(
        "testToken",
        "testName",
        "testEmail",
        "testPhotoUrl",
        "testProviderId"
    )

    @Before
    fun loginWithTestUser() {
        runBlocking {
            BackendApi.retrofitService.login(testUser)
        }
    }

    @After
    fun deleteTestUser() {
        runBlocking {
            BackendApi.retrofitService.updateUser(testUser)
        }
    }

    @Test
    fun testUpdateUser() {
        runBlocking {
            val updatedUser = UserNetworkModel(
                "testToken",
                "updatedName",
                "testEmail",
                "testPhotoUrl",
                "testProviderId"
            )
            BackendApi.retrofitService.updateUser(updatedUser)
        }
    }
}