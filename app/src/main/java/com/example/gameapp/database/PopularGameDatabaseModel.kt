package com.example.gameapp.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "popular_games")
data class PopularGameDatabaseModel(
    @PrimaryKey
    val gameId: Long,
)

// TODO: Investigate why not working
//data class PopularGame(
//    @Embedded val game: GameDatabaseModel,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "gameId"
//    )
//    val popularGame: PopularGamesDatabaseModel
//)