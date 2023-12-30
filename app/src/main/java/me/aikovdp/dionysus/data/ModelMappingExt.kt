package me.aikovdp.dionysus.data

import me.aikovdp.dionysus.data.source.network.NetworkMovie
import me.aikovdp.dionysus.data.source.network.NetworkMovieDetails
import java.time.LocalDate
import kotlin.time.Duration.Companion.minutes

fun NetworkMovie.toExternal() = Movie(
    id = id,
    title = title,
    posterUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/$posterPath"
)

fun NetworkMovieDetails.toExternal() = MovieDetails(
    id = id,
    title = title,
    posterUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/$posterPath",
    backdropUrl = "https://www.themoviedb.org/t/p/w1280/$backdropPath",
    releaseDate = LocalDate.parse(releaseDate),
    runtime = runtime.minutes,
    tagline = tagline,
    overview = overview
)
