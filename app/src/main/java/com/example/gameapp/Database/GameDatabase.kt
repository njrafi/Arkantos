package com.example.gameapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameDatabaseModel::class], version = 1)
abstract class GamesDatabase : RoomDatabase() {
    abstract val gamesDao: GamesDao


    companion object {
        private final const val DB_NAME = "Games_Database"
        private lateinit var instance: GamesDatabase


        fun getInstance(context: Context): GamesDatabase {
            synchronized(GamesDatabase::class.java) {
                if (!::instance.isInitialized) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        GamesDatabase::class.java,
                        DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}