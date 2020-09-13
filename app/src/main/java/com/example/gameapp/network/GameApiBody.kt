package com.example.gameapp.network

class GameApiBody(
    val fields: String =
        "name, summary, cover.image_id, storyline, rating, first_release_date, genres.name, platforms.name",
    val limit: Int = 50,
    val offset: Int = 0,
    var whereConditions: String = "cover.image_id != null",
) {
    fun getBodyString(): String {
        return "fields $fields;" +
                "limit $limit;" +
                "offset $offset;" +
                "where $whereConditions;"
    }

    fun addGenre(genre: GenreString) {
        whereConditions += " & genres = ${genre.id}"
    }

    enum class GenreString(val id: Int) {
        PointAndClick(2),
        Fighting(4),
        Shooter(5),
        Music(7),
        Platform(8),
        Puzzle(9),
        Racing(10),
        RealTimeStrategy(11),
        RolePlaying(12),
        Simulator(13),
        TurnBasedStrategy(16),
        Adventure(31)
    }
}