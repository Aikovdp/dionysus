package me.aikovdp.dionysus.data.source.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the movie table.
 */
@Dao
interface MovieDao {

    /**
     * Observes a single movie.
     *
     * @param movieId the movie id.
     * @return the movie with movieId
     */
    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun observeById(movieId: Int): Flow<LocalMovie>

    /**
     * Delete a movie by id
     *
     * @param movieId the movie id.
     */
    @Query("DELETE FROM movie WHERE id = :movieId")
    suspend fun deleteById(movieId: Int)
}
