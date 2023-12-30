package me.aikovdp.dionysus.data

import java.time.LocalDate
import kotlin.time.Duration

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
    val backdropUrl: String?,
    val runtime: Duration,
    val tagline: String,
    val overview: String
)

fun MovieDetails.toMovie() = Movie(
    id = id,
    title = title,
    posterUrl = posterUrl
)
