package com.example.gameapp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GamesDao {
    @Query("select * from games")
    suspend fun getAllGames(): List<GameDatabaseModel>

    @Query("select * from games where id = (:id)")
    suspend fun getGameById(id: Long?): List<GameDatabaseModel>

    @Query("select * from games where genres Like '%' || (:genre) || '%' ")
    suspend fun getGamesByGenre(genre: String?): List<GameDatabaseModel>

    @Query("select * from games where genres Like '%' || (:genre) || '%' limit (:limit) offset (:offset)")
    suspend fun getGamesByGenre(genre: String?, limit: Int, offset: Int): List<GameDatabaseModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: List<GameDatabaseModel>)
}