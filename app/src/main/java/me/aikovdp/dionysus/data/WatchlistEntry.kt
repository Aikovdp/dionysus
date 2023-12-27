package me.aikovdp.dionysus.data

import java.time.Instant

data class WatchlistEntry(
    val id: Int,
    val movie: Movie,
    val added: Instant
)
