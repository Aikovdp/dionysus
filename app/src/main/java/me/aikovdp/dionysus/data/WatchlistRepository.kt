package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    fun getEntriesStream(): Flow<List<WatchlistEntry>>
    suspend fun createEntry(movieId: Int): Int
    suspend fun createEntry(movie: Movie): Int
    suspend fun removeEntry(movieId: Int)
    fun containsMovieStream(movieId: Int): Flow<Boolean>
}
