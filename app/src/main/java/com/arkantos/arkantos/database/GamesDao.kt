package com.arkantos.arkantos.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GamesDao {
    @Query("select * from games")
    suspend fun getAllGames(): List<GameDatabaseModel>

    @Query("select * from games where id = (:id)")
    suspend fun getGameById(id: Long?): List<GameDatabaseModel>

    @Query("select * from games where genres Like '%' || (:genre) || '%' ")
    suspend fun getGamesByGenre(genre: String?): List<GameDatabaseModel>

    @Query("select * from games where genres Like '%' || (:genre) || '%' limit (:limit) offset (:offset)")
    suspend fun getGamesByGenre(genre: String, limit: Int, offset: Int): List<GameDatabaseModel>

    // TODO: Investigate why not working
//    @Transaction
//    @Query("select * from games")
//    suspend fun getPopularGames(): List<PopularGame>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: List<GameDatabaseModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(game: List<GameDatabaseModel>)

    // Popular Games
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPopularGames(game: List<PopularGameDatabaseModel>)

    @Transaction
    @Query("select games.* from games inner join popular_games on games.id = popular_games.gameId")
    suspend fun getPopularGames(): List<GameDatabaseModel>


    // Favorite Games
    @Transaction
    @Query("select games.* from games inner join favorite_games on games.id = favorite_games.gameId")
    fun getFavoriteGames(): LiveData<List<GameDatabaseModel>>

    @Transaction
    @Query("select games.* from games inner join favorite_games on games.id = favorite_games.gameId")
    suspend fun getFavoriteGamesSync(): List<GameDatabaseModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteGame(game: FavoriteGameDatabaseModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteGame(game: List<FavoriteGameDatabaseModel>)

    @Delete
    suspend fun deleteFavoriteGame(game: FavoriteGameDatabaseModel)
}