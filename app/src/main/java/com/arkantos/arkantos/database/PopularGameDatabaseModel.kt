package com.arkantos.arkantos.database

import androidx.room.Entity
import androidx.room.PrimaryKey

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