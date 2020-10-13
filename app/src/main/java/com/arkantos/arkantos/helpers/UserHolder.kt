package com.arkantos.arkantos.helpers

import com.arkantos.arkantos.helpers.SharedPreferenceHelper.get
import com.arkantos.arkantos.helpers.SharedPreferenceHelper.set
import com.arkantos.arkantos.network.models.UserNetworkModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object UserHolder {
    private const val userKey = "user"
    private var user: UserNetworkModel? = null
    private val jsonAdapter = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        .adapter(UserNetworkModel::class.java)

    fun setUser(user: UserNetworkModel?) {
        saveUserToSharedPreference(user)
        UserHolder.user = user
    }

    fun getUser(): UserNetworkModel {
        if (user == null) {
            user = getUserFromSharedPreference()
        }
        return user as UserNetworkModel
    }

    private fun saveUserToSharedPreference(user: UserNetworkModel?) {
        SharedPreferenceHelper.defaultPreferences[userKey] = jsonAdapter.toJson(user)
    }

    private fun getUserFromSharedPreference(): UserNetworkModel? {
        val userFromSharedPreference: String? = SharedPreferenceHelper.defaultPreferences[userKey]
        return jsonAdapter.fromJson(userFromSharedPreference ?: "")
    }
}