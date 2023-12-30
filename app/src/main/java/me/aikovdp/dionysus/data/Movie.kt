package me.aikovdp.dionysus.data

import java.time.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String
)

data class MovieDetails(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val releaseDate: LocalDate,
    val backdropUrl: String?
)
