package com.example.gameapp.network

class GameApiBody(
    private val fields: String =
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

    fun addGenre(genre: GenreString?) {
        if(genre != null)
            whereConditions += " & genres = ${genre.id}"
    }

    enum class SortParameters(val value: String) {
        Popularity("popularity"),
        Rating("rating"),
        AggregatedRating("aggregated_rating"),
        None("none")
    }
    enum class GenreString(val id: Int,val stringValue: String) {
        PointAndClick(2,"Point-and-click"),
        Fighting(4,"Fighting"),
        Shooter(5,"Shooter"),
        Music(7,"Music"),
        Platform(8,"Platform"),
        Puzzle(9,"Puzzle"),
        Racing(10,"Racing"),
        RealTimeStrategy(11,"Real Time Strategy (RTS)"),
        RolePlaying(12,"Role-playing (RPG)"),
        Simulator(13,"Simulator"),
        Sport(14,"Sport"),
        TurnBasedStrategy(16,"Turn Bases Strategy"),
        Adventure(31,"Adventure")
    }
}