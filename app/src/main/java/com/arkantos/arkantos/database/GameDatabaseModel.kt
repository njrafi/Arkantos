package com.arkantos.arkantos.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "games")
data class GameDatabaseModel(
    @PrimaryKey val id: Long,
    val name: String?,
    val summary: String?,
    val thumbnailUrl: String?,
    val coverImageUrl: String?,
    val storyline: String?,
    val rating: Int?,
    val releaseDate: Long?,
    val genres: List<String>?,
    val platforms: List<String>?,
    val updatedTime: Long = System.currentTimeMillis()
)