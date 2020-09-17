package com.example.gameapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_games")
data class FavoriteGameDatabaseModel(
    @PrimaryKey
    val gameId: Long,
)