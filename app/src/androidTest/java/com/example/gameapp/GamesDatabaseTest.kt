package com.example.gameapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.gameapp.database.GameDatabaseModel
import com.example.gameapp.database.GamesDao
import com.example.gameapp.database.GamesDatabase
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
            "dummySummary",
            "dummyUrl",
            null,
            "dummyStory",
            23,
            42,
            listOf("dummyGenre1,dummyGenre2"),
            listOf("dummyPlatform")

        )
        db?.gamesDao?.insert(game)
        val allGames = db?.gamesDao?.getAllGames()
        Assert.assertNotNull(allGames)

        if (allGames != null) {
            Assert.assertEquals(allGames[0].id, game.id)
            Assert.assertEquals(allGames[0].name, game.name)
            Assert.assertEquals(allGames[0].summary, game.summary)
            Assert.assertEquals(allGames[0].thumbnailUrl, game.thumbnailUrl)
            Assert.assertEquals(allGames[0].coverImageUrl, game.coverImageUrl)
            Assert.assertEquals(allGames[0].storyline, game.storyline)
            Assert.assertEquals(allGames[0].rating, game.rating)
            Assert.assertEquals(allGames[0].releaseDate, game.releaseDate)
            Assert.assertEquals(allGames[0].genres?.size, game.genres?.size)
            Assert.assertEquals(allGames[0].genres, game.genres)
            Assert.assertEquals(allGames[0].platforms?.size, game.platforms?.size)
            Assert.assertEquals(allGames[0].platforms, game.platforms)
        }
    }
}