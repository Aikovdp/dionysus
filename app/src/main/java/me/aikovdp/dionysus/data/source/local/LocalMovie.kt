package me.aikovdp.dionysus.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movie")
data class LocalMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val posterUrl: String
)
