package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.aikovdp.dionysus.data.source.local.LocalWatchlistEntry
import me.aikovdp.dionysus.data.source.local.MovieDao
import me.aikovdp.dionysus.data.source.local.WatchlistDao
import me.aikovdp.dionysus.data.source.network.MoviesNetworkDataSource
import java.time.Instant
import javax.inject.Inject

class LocalFirstWatchlistRepository @Inject constructor(
    private val watchlistDao: WatchlistDao,
    private val movieDao: MovieDao,
    private val movieDataSource: MoviesNetworkDataSource
) : WatchlistRepository {
    override fun getEntriesStream(): Flow<List<WatchlistEntry>> =
        watchlistDao.observeAll().map { list ->
            list.map {
                WatchlistEntry(
                    id = it.movieId,
                    added = it.addedAt,
                    movie = movieDao.getById(it.movieId).toExternal()
                )
            }
        }

    override suspend fun createEntry(movieId: Int): Int {
        val movie = movieDataSource.getMovie(movieId).toExternal().toMovie().toLocal()
        watchlistDao.insert(LocalWatchlistEntry(movieId, Instant.now()), movie)
        return movieId
    }

    override suspend fun removeEntry(movieId: Int) {
        watchlistDao.deleteById(movieId)
        movieDao.deleteById(movieId)
    }

    override fun containsMovieStream(movieId: Int): Flow<Boolean> =
        watchlistDao.observeIsInWatchlist(movieId)
}
