package me.aikovdp.dionysus.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.aikovdp.dionysus.data.FakeWatchlistRepository
import me.aikovdp.dionysus.data.WatchlistRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindWatchlistRepository(repository: FakeWatchlistRepository): WatchlistRepository
}
