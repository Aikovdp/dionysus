package me.aikovdp.dionysus.data.source.local

import androidx.room.TypeConverter
import java.time.Instant

class Converters {
    @TypeConverter
    fun instantToEpochMilli(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }

    @TypeConverter
    fun fromEpochMilli(epochMilli: Long?): Instant? {
        return epochMilli?.let(Instant::ofEpochMilli)
    }
}
