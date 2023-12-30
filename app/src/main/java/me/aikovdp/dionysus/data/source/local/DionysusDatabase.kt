package me.aikovdp.dionysus.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalMovie::class, LocalWatchlistEntry::class], version = 1)
abstract class DionysusDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun watchlistDao(): WatchlistDao
}
