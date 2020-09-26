package com.arkantos.arkantos.helpers

import com.arkantos.arkantos.network.models.UserNetworkModel
import com.squareup.moshi.Moshi

object UserHolder {
    private var user: UserNetworkModel? = null
    private val jsonAdapter = Moshi.Builder().build().adapter(UserNetworkModel::class.java)

    fun setUser(user: UserNetworkModel) {
        UserHolder.user = user
    }
}