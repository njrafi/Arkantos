package com.example.gameapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.gameapp.Database.GameDatabaseModel
import com.example.gameapp.Database.GamesDao
import com.example.gameapp.Database.GamesDatabase
import com.example.gameapp.network.GameNetworkModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GamesDatabaseTest {
    private var db: GamesDatabase? = null
    private var gamesDao: GamesDao? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
        db = Room.inMemoryDatabaseBuilder(context, GamesDatabase::class.java).build()
        gamesDao = db?.gamesDao
        Assert.assertNotNull(db)
        Assert.assertNotNull(gamesDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db?.close()
    }

    @Test
    fun testInsert() {
        val game = GameDatabaseModel(
            420,
            "dummyName",
            listOf("dummyGenre1","dummyGenre2")
        )
        db?.gamesDao?.insert(game)
        val allGames = db?.gamesDao?.getAllGames()
        Assert.assertNotNull(allGames)

        if(allGames != null) {
            Assert.assertEquals(allGames[0].id, game.id)
            Assert.assertEquals(allGames[0].name, game.name)
            Assert.assertEquals(allGames[0].genres?.size, game.genres?.size)
            Assert.assertEquals(allGames[0].genres, game.genres)
        }
    }
}