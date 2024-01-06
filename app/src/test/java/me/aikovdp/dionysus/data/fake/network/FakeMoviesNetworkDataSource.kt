package me.aikovdp.dionysus.data.fake.network

import me.aikovdp.dionysus.data.source.network.MoviesNetworkDataSource
import me.aikovdp.dionysus.data.source.network.NetworkMovieDetails
import me.aikovdp.dionysus.data.source.network.NetworkSearchResults

class FakeMoviesNetworkDataSource : MoviesNetworkDataSource {
    override suspend fun getMovie(id: Int): NetworkMovieDetails =
        NetworkMovieDetails(
            id = id,
            title = "",
            posterPath = null,
            releaseDate = "2001-09-05",
            backdropPath = null,
            runtime = 149,
            tagline = "",
            overview = ""
        )

    override suspend fun searchMovies(query: String): NetworkSearchResults {
        throw NotImplementedError()
    }
}
