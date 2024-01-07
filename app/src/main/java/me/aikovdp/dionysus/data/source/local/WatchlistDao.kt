package me.aikovdp.dionysus.data.source.local

import androidx.room.Dao
import androidx.room.Insert
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
     * Inserts a watchlist entry in the database.
     *
     * @param entry the entry to be inserted.
     */
    @Insert
    suspend fun insert(entry: LocalWatchlistEntry)

    /**
     * Deletes a watchlist entry by movie id
     *
     * @param movieId the movie id.
     */
    @Query("DELETE FROM watchlist WHERE movieId = :movieId")
    suspend fun deleteById(movieId: Int)

    /**
     * Observes whether a movie is in the watchlist.
     *
     * @param movieId the movie id.
     * @return whether the movie is in the watchlist.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE movieId = :movieId)")
    fun observeIsInWatchlist(movieId: Int): Flow<Boolean>

    /**
     * Checks whether a movie is in the watchlist.
     *
     * @param movieId the movie id.
     * @return whether the movie is in the watchlist.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE movieId = :movieId)")
    suspend fun isInWatchlist(movieId: Int): Boolean
}
