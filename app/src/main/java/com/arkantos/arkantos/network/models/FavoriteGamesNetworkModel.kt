package com.arkantos.arkantos.network.models

import com.arkantos.arkantos.database.GameDatabaseModel
import com.arkantos.arkantos.helpers.UserHolder

data class FavoriteGamesNetworkModel(
    val userToken: String,
    val favoriteGames: List<GameDatabaseModel>
)

data class FavoriteGame(
    val id: Long,
    val name: String?
)

fun List<GameDatabaseModel>.asFavoriteGamesNetworkModel(): FavoriteGamesNetworkModel {
    return FavoriteGamesNetworkModel(UserHolder.getUser().token, this)
}