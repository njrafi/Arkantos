package com.arkantos.arkantos

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkantos.arkantos.helpers.SharedPreferenceHelper
import com.arkantos.arkantos.helpers.SharedPreferenceHelper.get
import com.arkantos.arkantos.helpers.SharedPreferenceHelper.set
import com.arkantos.arkantos.network.models.UserNetworkModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedPreferenceTest {
    @Test
    fun testString() {
        val key = "key"
        val value = "value"
        SharedPreferenceHelper.defaultPreferences[key] = value
        val valueFromPref: String? = SharedPreferenceHelper.defaultPreferences[key]
        Assert.assertEquals(valueFromPref, value)
    }

    @Test
    fun testUser() {
        val key = "key"
        val value = UserNetworkModel("a", "b", "c", "d", "e")
        val jsonAdapter = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            .adapter(UserNetworkModel::class.java)
        SharedPreferenceHelper.defaultPreferences[key] = jsonAdapter.toJson(value)
        val stringValueFromPref: String? = SharedPreferenceHelper.defaultPreferences[key]
        Assert.assertNotNull(stringValueFromPref)
        val valueFromPref = jsonAdapter.fromJson(stringValueFromPref ?: "null")
        Assert.assertEquals(valueFromPref?.token, value.token)
        Assert.assertEquals(valueFromPref?.name, value.name)
        Assert.assertEquals(valueFromPref?.email, value.email)
    }
}