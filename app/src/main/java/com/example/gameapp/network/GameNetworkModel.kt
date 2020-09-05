package com.example.gameapp.network

import com.squareup.moshi.Json

data class GameNetworkModel(val id: Long,
                            val name: String,
                            val summary: String,
                            @Json(name = "cover") val coverId: Long
                            )