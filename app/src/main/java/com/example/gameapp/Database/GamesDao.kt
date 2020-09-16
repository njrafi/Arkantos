package com.example.gameapp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gameapp.network.GameNetworkModel

@Dao
interface GamesDao {
    @Query("select * from games")
    fun getAllGames(): List<GameDatabaseModel>

    @Insert
    fun insert(game: GameDatabaseModel)
}