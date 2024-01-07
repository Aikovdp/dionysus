package me.aikovdp.dionysus.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import me.aikovdp.dionysus.data.DiaryRepository
import me.aikovdp.dionysus.data.LocalDiaryRepository
import me.aikovdp.dionysus.data.LocalWatchlistRepository
import me.aikovdp.dionysus.data.MovieRepository
import me.aikovdp.dionysus.data.NetworkMovieRepository
import me.aikovdp.dionysus.data.WatchlistRepository
import me.aikovdp.dionysus.data.source.local.DiaryDao
import me.aikovdp.dionysus.data.source.local.DionysusDatabase
import me.aikovdp.dionysus.data.source.local.MovieDao
import me.aikovdp.dionysus.data.source.local.WatchlistDao
import me.aikovdp.dionysus.data.source.network.MoviesNetworkDataSource
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindWatchlistRepository(repository: LocalWatchlistRepository): WatchlistRepository

    @Singleton
    @Binds
    abstract fun bindDiaryRepository(repository: LocalDiaryRepository): DiaryRepository

    @Singleton
    @Binds
    abstract fun bindMovieRepository(repository: NetworkMovieRepository): MovieRepository
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

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DionysusDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DionysusDatabase::class.java,
            "Dionysus.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieDao(database: DionysusDatabase): MovieDao = database.movieDao()

    @Provides
    fun provideWatchlistDao(database: DionysusDatabase): WatchlistDao = database.watchlistDao()

    @Provides
    fun provideDiaryDao(database: DionysusDatabase): DiaryDao = database.diaryDao()
}
