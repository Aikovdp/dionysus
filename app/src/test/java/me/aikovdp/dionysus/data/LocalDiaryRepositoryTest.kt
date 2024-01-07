package me.aikovdp.dionysus.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import me.aikovdp.dionysus.data.fake.local.FakeDiaryDao
import me.aikovdp.dionysus.data.fake.local.FakeMovieDao
import me.aikovdp.dionysus.data.fake.local.FakeWatchlistDao
import me.aikovdp.dionysus.data.fake.network.FakeMoviesNetworkDataSource
import me.aikovdp.dionysus.data.source.local.DiaryDao
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class LocalDiaryRepositoryTest {
    // Test dependencies
    private lateinit var moviesNetworkDataSource: FakeMoviesNetworkDataSource
    private lateinit var movieDao: FakeMovieDao
    private lateinit var watchlistDao: FakeWatchlistDao
    private lateinit var diaryDao: DiaryDao

    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)

    // Class under test
    private lateinit var diaryRepository: LocalDiaryRepository

    @Before
    fun createRepository() {
        watchlistDao = FakeWatchlistDao()
        movieDao = FakeMovieDao()
        diaryDao = FakeDiaryDao()
        moviesNetworkDataSource = FakeMoviesNetworkDataSource()
        diaryRepository = LocalDiaryRepository(
            diaryDao,
            watchlistDao,
            movieDao,
            moviesNetworkDataSource
        )
    }

    @Test
    fun getEntries_newRepository_isEmpty() = testScope.runTest {
        val items = diaryRepository.getEntriesStream().first()

        assert(items.isEmpty())
    }

    @Test
    fun addEntry_updatesFlow() = testScope.runTest {
        val movieId = 123
        val diaryFlow = diaryRepository.getEntriesStream()
        diaryRepository.createEntry(movieId, LocalDate.now())

        assert(diaryFlow.first().any { it.movie.id ==  movieId})
    }

    @Test
    fun removeEntry_updatesFlow() = testScope.runTest {
        val movieId = 123
        val diaryFlow = diaryRepository.getEntriesStream()
        diaryRepository.createEntry(movieId, LocalDate.now())

        diaryRepository.removeEntry(0)


        assert(diaryFlow.first().isEmpty())
    }
}
