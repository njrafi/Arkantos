package com.example.gameapp.network

import com.example.gameapp.Database.GameDatabaseModel
import com.example.gameapp.domain.Game
import kotlin.math.roundToInt

data class GameNetworkModel(
    val id: Long?,
    val name: String?,
    val summary: String?,
    val cover: GameCover?,
    val storyline: String?,
    val rating: Double?,
    val first_release_date: Long?,
    val genres: List<Genre>?,
    val platforms: List<Platform>?
)

data class GameCover(val image_id: String)
data class Genre(val name: String)
data class Platform(val name: String)

fun List<GameNetworkModel>.asDomainModel(): List<Game> {
    return map { game ->
        Game(
            id = game.id,
            name = game.name,
            summary = game.summary,
            thumbnailUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/"
                    + game.cover?.image_id + ".jpg",
            coverImageUrl = "https://images.igdb.com/igdb/image/upload/t_1080p/"
                    + game.cover?.image_id + ".jpg",
            storyline = game.storyline,
            rating = game.rating?.roundToInt(),
            releaseDate = game.first_release_date,
            genres = game.genres?.map {
                it.name
            },
            platforms = game.platforms?.map {
                it.name
            }
        )
    }
}

fun List<GameNetworkModel>.asDatabaseModel(): List<GameDatabaseModel> {
    return asDomainModel().map { game ->
        GameDatabaseModel(
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