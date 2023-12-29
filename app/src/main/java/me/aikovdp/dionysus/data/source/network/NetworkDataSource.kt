package me.aikovdp.dionysus.data.source.network

import me.aikovdp.dionysus.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkDataSource {

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_READ_ACCESS_TOKEN}")
    @GET("3/movie/{id}")
    suspend fun getMovie(@Path("id") id: Int): NetworkMovie

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_READ_ACCESS_TOKEN}")
    @GET("3/search/movie")
    suspend fun search(@Query("query") query: String): NetworkSearchResults
}
