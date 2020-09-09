package com.example.gameapp.network

import com.example.gameapp.domain.Game
import com.squareup.moshi.Json

data class GameNetworkModel(val id: Long?,
                            val name: String?,
                            val summary: String?,
                            val cover: GameCover?,
                            val storyline: String?
                            )

data class GameCover(val image_id: String)

fun List<GameNetworkModel>.asDomainModel(): List<Game> {
    return map {
        Game(
            id = it.id,
            name = it.name,
            summary = it.summary,
            thumbnailUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/"
                    + it.cover?.image_id + ".jpg",
            coverImageUrl = "https://images.igdb.com/igdb/image/upload/t_1080p/"
                    + it.cover?.image_id + ".jpg",
            storyline = it.storyline
        )
    }
}