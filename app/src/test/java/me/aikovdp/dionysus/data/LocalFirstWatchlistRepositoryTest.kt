package me.aikovdp.dionysus.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import me.aikovdp.dionysus.data.fake.local.FakeMovieDao
import me.aikovdp.dionysus.data.fake.local.FakeWatchlistDao
import me.aikovdp.dionysus.data.fake.network.FakeMoviesNetworkDataSource
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocalFirstWatchlistRepositoryTest {
    // Test dependencies
    private lateinit var moviesNetworkDataSource: FakeMoviesNetworkDataSource
    private lateinit var movieDao: FakeMovieDao
    private lateinit var watchlistDao: FakeWatchlistDao

    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)

    // Class under test
    private lateinit var watchlistRepository: LocalFirstWatchlistRepository

    @Before
    fun createRepository() {
        watchlistDao = FakeWatchlistDao()
        movieDao = FakeMovieDao()
        moviesNetworkDataSource = FakeMoviesNetworkDataSource()
        watchlistRepository = LocalFirstWatchlistRepository(
            watchlistDao,
            movieDao,
            moviesNetworkDataSource
        )
    }

    @Test
    fun getEntries_newRepository_isEmpty() = testScope.runTest {
        val items = watchlistRepository.getEntriesStream().first()

        assert(items.isEmpty())
    }

    @Test
    fun addEntry_updatesFlow() = testScope.runTest {
        val movieId = 123
        val watchlistFlow = watchlistRepository.getEntriesStream()
        watchlistRepository.createEntry(movieId)

        assert(watchlistFlow.first().any { it.movie.id ==  movieId})
    }

    @Test
    fun removeEntry_updatesFlow() = testScope.runTest {
        val movieId = 123
        val watchlistFlow = watchlistRepository.getEntriesStream()
        watchlistRepository.createEntry(movieId)

        watchlistRepository.removeEntry(movieId)


        assert(watchlistFlow.first().isEmpty())
    }
}
