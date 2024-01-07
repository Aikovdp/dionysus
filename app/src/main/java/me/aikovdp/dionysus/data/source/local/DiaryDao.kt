package me.aikovdp.dionysus.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    /**
     * Observes a list of diary entries.
     *
     * @return all diary entries.
     */
    @Query("SELECT * FROM diary")
    fun observeAll(): Flow<List<LocalDiaryEntry>>

    /**
     * Gets a diary entry by id.
     *
     * @param id the entry id.
     */
    @Query("SELECT * from diary WHERE id = :id")
    suspend fun getById(id: Int): LocalDiaryEntry

    /**
     * Inserts a diary entry in the database.
     *
     * @param entry the entry to be inserted.
     */
    @Insert
    suspend fun insert(entry: LocalDiaryEntry)

    /**
     * Deletes a diary entry by id
     *
     * @param id the entry id.
     */
    @Query("DELETE FROM diary WHERE id = :id")
    suspend fun deleteById(id: Int)

    /**
     * Checks whether a movie is in the watchlist.
     *
     * @param movieId the movie id.
     * @return whether the movie is in the watchlist.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM diary WHERE movieId = :movieId)")
    suspend fun isInDiary(movieId: Int): Boolean
}
