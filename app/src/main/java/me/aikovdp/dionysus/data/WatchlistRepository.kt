package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    fun getEntriesStream(): Flow<List<WatchlistEntry>>
    suspend fun createEntry(movie: Movie): Int
    suspend fun deleteEntry(entryId: Int)
}
