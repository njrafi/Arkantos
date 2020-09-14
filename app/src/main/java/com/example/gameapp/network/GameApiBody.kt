package com.example.gameapp.network

class GameApiBody(
    val fields: String =
        "name, summary, cover.image_id, storyline, rating, first_release_date, genres.name, platforms.name",
    val limit: Int = 50,
    val offset: Int = 0,
    var whereConditions: String = "cover.image_id != null",
    var sortParameter: SortParameters = GameApiBody.SortParameters.None
) {
    fun getBodyString(): String {
        return "fields $fields;" +
                "limit $limit;" +
                "offset $offset;" +
                "where $whereConditions;" +
                getSortString()
    }

    private fun getSortString(): String {
        if(sortParameter == SortParameters.None) return ";"
        return "sort ${sortParameter.value} desc;"
    }

    fun addGenre(genre: GenreString) {
        whereConditions += " & genres = ${genre.id}"
    }

    enum class SortParameters(val value: String) {
        Popularity("popularity"),
        Rating("rating"),
        AggregatedRating("aggregated_rating"),
        None("none")
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