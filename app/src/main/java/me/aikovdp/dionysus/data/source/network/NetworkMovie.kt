package me.aikovdp.dionysus.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMovie(
    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPath: String?
)

@Serializable
data class NetworkMovieDetails(
    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("backdrop_path")
    val backdropPath: String?
)
