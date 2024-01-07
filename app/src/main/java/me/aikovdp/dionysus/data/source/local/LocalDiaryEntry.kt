package me.aikovdp.dionysus.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity("diary")
data class LocalDiaryEntry(
    @PrimaryKey val id: Int?,
    val movieId: Int,
    val addedAt: LocalDate
)
