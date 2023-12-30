package me.aikovdp.dionysus.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity("watchlist")
data class LocalWatchlistEntry(
    @PrimaryKey val movieId: Int,
    val addedAt: Instant
)
