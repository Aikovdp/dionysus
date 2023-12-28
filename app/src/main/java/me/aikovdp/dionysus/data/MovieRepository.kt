package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovieStream(movieId: Int): Flow<Movie?>
}
