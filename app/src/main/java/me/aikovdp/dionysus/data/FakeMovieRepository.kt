package me.aikovdp.dionysus.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeMovieRepository @Inject constructor() : MovieRepository {
    private val movies = mutableListOf(
        Movie(
            1,
            "La La Land",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg"
        ),
        Movie(
            2,
            "Parasite",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg"
        )
    )

    override fun getMovieStream(movieId: Int): Flow<Movie?> = flow {
        while (true) {
            emit(movies.find { it.id == movieId })
            delay(5_000)
        }
    }

    override fun searchMovies(query: String): List<Movie> =
        if (query.isBlank()) {
            listOf()
        } else {
            movies.filter { it.title.contains(query, ignoreCase = false) }
        }
}
