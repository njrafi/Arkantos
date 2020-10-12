package com.arkantos.arkantos.network.models

import com.google.firebase.auth.FirebaseUser
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserNetworkModel(
    val token: String,
    var name: String?,
    var email: String?,
    val photoUrl: String?,
    val providerId: String?
)

data class SignUpResponseNetworkModel(
    val message: String?,
    val user: UserNetworkModel
)

fun FirebaseUser.asNetworkModel(): UserNetworkModel {
    // TODO: Change uid to token
    return UserNetworkModel(uid, displayName, email, photoUrl.toString(), providerId)
}


