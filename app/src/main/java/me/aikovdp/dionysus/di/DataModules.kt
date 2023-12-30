package me.aikovdp.dionysus.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import me.aikovdp.dionysus.data.DionysusMovieRepository
import me.aikovdp.dionysus.data.FakeWatchlistRepository
import me.aikovdp.dionysus.data.MovieRepository
import me.aikovdp.dionysus.data.WatchlistRepository
import me.aikovdp.dionysus.data.source.network.MoviesNetworkDataSource
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindWatchlistRepository(repository: FakeWatchlistRepository): WatchlistRepository

    @Singleton
    @Binds
    abstract fun bindMovieRepository(repository: DionysusMovieRepository): MovieRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Singleton
    @Provides
    fun provideMoviesNetworkDataSource(): MoviesNetworkDataSource =
        retrofit.create(MoviesNetworkDataSource::class.java)
}
