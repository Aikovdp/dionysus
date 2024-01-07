package me.aikovdp.dionysus.data.source.local

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun instantToEpochMilli(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }

    @TypeConverter
    fun fromEpochMilli(epochMilli: Long?): Instant? {
        return epochMilli?.let(Instant::ofEpochMilli)
    }

    @TypeConverter
    fun localDateToString(localDate: LocalDate?): String? {
        return localDate?.toString()
    }

    @TypeConverter
    fun fromString(string: String?): LocalDate? {
        return string?.let(LocalDate::parse)
    }
}
