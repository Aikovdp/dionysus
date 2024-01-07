package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.aikovdp.dionysus.data.source.local.DiaryDao
import me.aikovdp.dionysus.data.source.local.LocalDiaryEntry
import me.aikovdp.dionysus.data.source.local.MovieDao
import me.aikovdp.dionysus.data.source.local.WatchlistDao
import me.aikovdp.dionysus.data.source.network.MoviesNetworkDataSource
import java.time.LocalDate
import javax.inject.Inject

class LocalDiaryRepository @Inject constructor(
    private val diaryDao: DiaryDao,
    private val watchlistDao: WatchlistDao,
    private val movieDao: MovieDao,
    private val movieDataSource: MoviesNetworkDataSource
) : DiaryRepository {
    override fun getEntriesStream(): Flow<List<DiaryEntry>> =
        diaryDao.observeAll().map { list ->
            list.map {
                val movie = movieDao.getById(it.movieId)
                    ?: throw IllegalStateException("This shouldn't happen")
                DiaryEntry(
                    id = it.id!!,
                    added = it.addedAt,
                    movie = movie.toExternal()
                )
            }
        }

    override suspend fun createEntry(movieId: Int, date: LocalDate) {
        val movie = movieDataSource.getMovie(movieId).toExternal().toMovie().toLocal()
        movieDao.upsert(movie)
        diaryDao.insert(LocalDiaryEntry(null, movieId, date))
    }

    override suspend fun removeEntry(id: Int) {
        val movieId = diaryDao.getById(id).movieId
        diaryDao.deleteById(id)
        if (!watchlistDao.isInWatchlist(movieId)
            && diaryDao.getAll() // Ensure no other diary entry references the movie
                .filterNot { it.id == id }
                .none { it.movieId == movieId }
        ) {
            movieDao.deleteById(movieId)
        }
    }
}
