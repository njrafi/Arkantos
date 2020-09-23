package com.arkantos.arkantos.network.models

import com.arkantos.arkantos.database.GameDatabaseModel
import com.arkantos.arkantos.domain.asDomainModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class FavoriteGamesNetworkModel(
    val userToken: String,
    val favoriteGames: List<FavoriteGame>
)

data class FavoriteGame(
    val id: Long,
    val name: String?
)

fun List<GameDatabaseModel>.asFavoriteGamesNetworkModel(): FavoriteGamesNetworkModel {
    val userId = Firebase.auth.currentUser?.uid
    if (userId != null)
        return FavoriteGamesNetworkModel(userId, map { game ->
            FavoriteGame(game.id, game.name)
        })
    else {
        throw Throwable("User not found")
    }
}