package me.aikovdp.dionysus.data

import me.aikovdp.dionysus.data.source.network.NetworkMovie

fun NetworkMovie.toExternal() = Movie(
    id = id,
    title = title,
    posterUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/$posterPath"
)
