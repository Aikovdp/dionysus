package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.aikovdp.dionysus.data.source.network.MoviesNetworkDataSource
import javax.inject.Inject

class NetworkMovieRepository @Inject constructor(
    private val moviesNetworkDataSource: MoviesNetworkDataSource
) : MovieRepository {
    override suspend fun getMovie(movieId: Int): MovieDetails {
        return moviesNetworkDataSource.getMovie(movieId).toExternal()
    }

    override fun getMovieStream(movieId: Int): Flow<MovieDetails> = flow {
        emit(getMovie(movieId))
    }

    override suspend fun searchMovies(query: String): List<Movie> =
        moviesNetworkDataSource.searchMovies(query).results.map { it.toExternal() }
}
