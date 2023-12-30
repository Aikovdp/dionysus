package me.aikovdp.dionysus.data.source.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the watchlist table.
 */
@Dao
interface WatchlistDao {

    /**
     * Observes a list of watchlist entries.
     *
     * @return all watchlist entries.
     */
    @Query("SELECT * FROM watchlist")
    fun observeAll(): Flow<List<LocalWatchlistEntry>>

    /**
     * Observes a single watchlist entry.
     *
     * @param movieId the movie id.
     * @return the watchlist entry with movieId
     */
    @Query("SELECT * FROM watchlist WHERE movieId = :movieId")
    fun observeById(movieId: Int): Flow<LocalWatchlistEntry>

    /**
     * Delete a watchlist entry by movie id
     *
     * @param movieId the movie id.
     */
    @Query("DELETE FROM watchlist WHERE movieId = :movieId")
    suspend fun deleteById(movieId: Int)
}
