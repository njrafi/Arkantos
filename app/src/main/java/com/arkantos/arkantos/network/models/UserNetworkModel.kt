package com.arkantos.arkantos.network.models

import com.google.firebase.auth.FirebaseUser
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserNetworkModel(
    val token: String,
    val name: String?,
    val email: String?,
    val photoUrl: String?,
    val providerId: String?
)

fun FirebaseUser.asNetworkModel(): UserNetworkModel {
    // TODO: Change uid to token
    return UserNetworkModel(uid,displayName,email,photoUrl.toString(),providerId)
}


