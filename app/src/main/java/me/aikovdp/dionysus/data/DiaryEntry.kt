package me.aikovdp.dionysus.data

import java.time.LocalDate

data class DiaryEntry(
    val id: Int,
    val movie: Movie,
    val added: LocalDate
)
