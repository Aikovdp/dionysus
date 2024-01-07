package me.aikovdp.dionysus.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import me.aikovdp.dionysus.data.source.local.DiaryDao
import me.aikovdp.dionysus.data.source.local.DionysusDatabase
import me.aikovdp.dionysus.data.source.local.MovieDao
import me.aikovdp.dionysus.data.source.local.WatchlistDao
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object DatabaseTestModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): DionysusDatabase {
        return Room
            .inMemoryDatabaseBuilder(context.applicationContext, DionysusDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }


    @Provides
    fun provideMovieDao(database: DionysusDatabase): MovieDao = database.movieDao()

    @Provides
    fun provideWatchlistDao(database: DionysusDatabase): WatchlistDao = database.watchlistDao()

    @Provides
    fun provideDiaryDao(database: DionysusDatabase): DiaryDao = database.diaryDao()
}
