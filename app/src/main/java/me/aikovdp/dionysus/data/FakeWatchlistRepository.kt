package me.aikovdp.dionysus.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import javax.inject.Inject

class FakeWatchlistRepository @Inject constructor() : WatchlistRepository {
    private val watchlist = mutableListOf(
        WatchlistEntry(1, Movie(1, "La La Land"), Instant.now()),
        WatchlistEntry(2, Movie(2, "Parasite"), Instant.now())
    )

    override fun getEntriesStream(): Flow<List<WatchlistEntry>> = flow {
        while (true) {
            emit(watchlist)
            delay(5_000)
        }
    }

    override fun createEntry(movie: Movie): Int {
        val id = watchlist.maxOf { it.id } + 1
        watchlist.add(WatchlistEntry(id, movie, Instant.now()))
        return id
    }

    override fun deleteEntry(entryId: Int) {
        watchlist.removeIf { it.id == entryId }
    }

}
