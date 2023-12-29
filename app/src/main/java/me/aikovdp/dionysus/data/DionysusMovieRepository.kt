package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.aikovdp.dionysus.data.source.network.NetworkDataSource
import javax.inject.Inject

class DionysusMovieRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : MovieRepository {
    override fun getMovieStream(movieId: Int): Flow<Movie?> = flow {
        emit(networkDataSource.getMovie(movieId).toExternal())
    }

    override suspend fun searchMovies(query: String): List<Movie> =
        networkDataSource.search(query).results.map { it.toExternal() }
}
