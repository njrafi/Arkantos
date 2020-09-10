package com.example.gameapp.domain

import com.example.gameapp.network.Platform

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