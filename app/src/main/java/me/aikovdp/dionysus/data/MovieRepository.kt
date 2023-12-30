package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovie(movieId: Int): MovieDetails

    fun getMovieStream(movieId: Int): Flow<MovieDetails>

    suspend fun searchMovies(query: String): List<Movie>
}
