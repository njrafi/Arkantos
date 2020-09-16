package com.example.gameapp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gameapp.network.GameNetworkModel

@Dao
interface GamesDao {
    @Query("select * from games")
    suspend fun getAllGames(): List<GameDatabaseModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: List<GameDatabaseModel>)
}