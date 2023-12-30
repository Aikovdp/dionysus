package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.aikovdp.dionysus.data.source.network.MoviesNetworkDataSource
import javax.inject.Inject

class DionysusMovieRepository @Inject constructor(
    private val moviesNetworkDataSource: MoviesNetworkDataSource
) : MovieRepository {
    override suspend fun getMovie(movieId: Int): Movie {
        return moviesNetworkDataSource.getMovie(movieId).toExternal()
    }

    override fun getMovieStream(movieId: Int): Flow<Movie> = flow {
        emit(getMovie(movieId))
    }

    override suspend fun searchMovies(query: String): List<Movie> =
        moviesNetworkDataSource.search(query).results.map { it.toExternal() }
}
