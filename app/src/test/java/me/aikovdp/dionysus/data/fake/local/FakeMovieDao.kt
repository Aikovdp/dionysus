package me.aikovdp.dionysus.data.fake.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import me.aikovdp.dionysus.data.source.local.LocalMovie
import me.aikovdp.dionysus.data.source.local.MovieDao

class FakeMovieDao(initialMovies: List<LocalMovie> = emptyList()): MovieDao {
    private val movies = initialMovies.toMutableList()

    private val movieStream = MutableStateFlow(movies)

    override fun observeById(movieId: Int): Flow<LocalMovie> =
        movieStream.map { list -> list.find { it.id == movieId } ?: throw NoSuchElementException() }

    override suspend fun getById(movieId: Int): LocalMovie =
        movies.find { it.id == movieId } ?: throw NoSuchElementException("No movie with id $movieId")

    override suspend fun upsert(movie: LocalMovie) {
        movies.removeIf { it.id == movie.id }
        movies.add(movie)
        movieStream.update { movies }
    }

    override suspend fun deleteById(movieId: Int) {
        movies.removeIf { it.id == movieId }
        movieStream.update { movies }
    }
}
