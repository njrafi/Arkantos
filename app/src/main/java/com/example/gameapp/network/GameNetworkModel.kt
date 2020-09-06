package com.example.gameapp.network

import com.example.gameapp.domain.Game
import com.squareup.moshi.Json

data class GameNetworkModel(val id: Long?,
                            val name: String?,
                            val summary: String?,
                            @Json(name = "cover") val coverId: Long?
                            )

fun List<GameNetworkModel>.asDomainModel(): List<Game> {
    return map {
        Game(
            id = it.id,
            name = it.name,
            summary = it.summary
        )
    }
}