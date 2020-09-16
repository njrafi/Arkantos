package com.example.gameapp.domain

import com.example.gameapp.Database.GameDatabaseModel
import com.example.gameapp.network.GameNetworkModel
import com.example.gameapp.network.Platform
import com.example.gameapp.network.asDomainModel

data class Game(
    val id: Long?,
    val name: String?,
    val summary: String?,
    val thumbnailUrl: String?,
    val coverImageUrl: String?,
    val storyline: String?,
    val rating: Int?,
    val releaseDate: Long?,
    val genres: List<String>?,
    val platforms: List<String>?
)

fun List<GameDatabaseModel>.asDomainModel(): List<Game> {
    return map { game ->
        Game(
            id = game.id,
            name = game.name,
            summary = game.summary,
            storyline = game.storyline,
            thumbnailUrl = game.thumbnailUrl,
            coverImageUrl = game.coverImageUrl,
            rating = game.rating,
            releaseDate = game.releaseDate,
            genres = game.genres,
            platforms = game.platforms
        )
    }
}