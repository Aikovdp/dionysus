package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    fun getEntriesStream(): Flow<List<WatchlistEntry>>
    fun createEntry(movie: Movie): Int
    fun deleteEntry(entryId: Int)
}
