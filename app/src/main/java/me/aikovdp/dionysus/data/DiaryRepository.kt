package me.aikovdp.dionysus.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DiaryRepository {
    fun getEntriesStream(): Flow<List<DiaryEntry>>
    suspend fun createEntry(movieId: Int, date: LocalDate)
    suspend fun removeEntry(id: Int)
}
