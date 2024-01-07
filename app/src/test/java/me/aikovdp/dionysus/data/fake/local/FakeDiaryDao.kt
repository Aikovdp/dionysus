package me.aikovdp.dionysus.data.fake.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import me.aikovdp.dionysus.data.source.local.DiaryDao
import me.aikovdp.dionysus.data.source.local.LocalDiaryEntry

class FakeDiaryDao(
    initialEntries: List<LocalDiaryEntry> = emptyList()
) : DiaryDao {

    private val entries = initialEntries.toMutableList()

    private val entryStream = MutableStateFlow(entries)
    override fun observeAll(): Flow<List<LocalDiaryEntry>> = entryStream
    override suspend fun getAll(): List<LocalDiaryEntry> = entries.toList()

    override suspend fun getById(id: Int): LocalDiaryEntry {
        return entries.find { it.id == id } ?: throw NoSuchElementException()
    }

    override suspend fun insert(entry: LocalDiaryEntry) {
        entries.removeIf { it.id == entry.id }
        entries.add(
            LocalDiaryEntry(
                id = entries.size,
                movieId = entry.movieId,
                addedAt = entry.addedAt
            )
        )
        entryStream.update { entries }
    }

    override suspend fun deleteById(id: Int) {
        entries.removeIf { it.id == id }
        entryStream.update { entries }
    }

    override suspend fun isInDiary(movieId: Int): Boolean =
        entries.any { it.movieId == movieId }
}
