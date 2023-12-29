package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovie(movieId: Int): Movie

    fun getMovieStream(movieId: Int): Flow<Movie>

    suspend fun searchMovies(query: String): List<Movie>
}
