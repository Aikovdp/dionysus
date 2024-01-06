package me.aikovdp.dionysus.data.fake.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import me.aikovdp.dionysus.data.source.local.LocalWatchlistEntry
import me.aikovdp.dionysus.data.source.local.WatchlistDao

class FakeWatchlistDao(
    initialEntries: List<LocalWatchlistEntry> = emptyList()
) : WatchlistDao {

    private val entries = initialEntries.toMutableList()

    private val entryStream = MutableStateFlow(entries)
    override fun observeAll(): Flow<List<LocalWatchlistEntry>> = entryStream

    override fun observeById(movieId: Int): Flow<LocalWatchlistEntry> =
        entryStream.map {
            it.find { entry -> entry.movieId == movieId } ?: throw NoSuchElementException()
        }

    override suspend fun insert(entry: LocalWatchlistEntry) {
        entries.removeIf { it.movieId == entry.movieId }
        entries.add(entry)
        entryStream.update { entries }
    }

    override suspend fun deleteById(movieId: Int) {
        entries.removeIf { it.movieId == movieId }
        entryStream.update { entries }
    }

    override fun observeIsInWatchlist(movieId: Int): Flow<Boolean> =
        entryStream.map { list -> list.any { it.movieId == movieId } }
}
