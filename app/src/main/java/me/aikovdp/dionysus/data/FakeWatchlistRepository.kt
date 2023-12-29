package me.aikovdp.dionysus.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import javax.inject.Inject

class FakeWatchlistRepository @Inject constructor() : WatchlistRepository {
    private val watchlist = mutableListOf(
        WatchlistEntry(
            1,
            Movie(
                313369,
                "La La Land",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg"
            ),
            Instant.now()
        ),
        WatchlistEntry(
            2,
            Movie(
                496243,
                "Parasite",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg"
            ),
            Instant.now()
        )
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
