package me.aikovdp.dionysus.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity("diary")
data class LocalDiaryEntry(
    @PrimaryKey val id: Int,
    val movieId: Int,
    val addedAt: Instant
)
