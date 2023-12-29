package me.aikovdp.dionysus.data.source.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkSearchResults(
    val results: List<NetworkMovie>
)
