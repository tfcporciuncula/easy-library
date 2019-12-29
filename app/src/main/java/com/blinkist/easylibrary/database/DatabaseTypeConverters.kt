package com.blinkist.easylibrary.database

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

object DatabaseTypeConverters {

  @JvmStatic @TypeConverter
  fun toOffsetDateTime(value: String?) = value?.let { OffsetDateTime.parse(value) }

  @JvmStatic @TypeConverter
  fun fromOffsetDateTime(date: OffsetDateTime?) = date?.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

  @JvmStatic @TypeConverter
  fun toLocalDate(value: String?) = value?.let { LocalDate.parse(value) }

  @JvmStatic @TypeConverter
  fun fromLocalDate(date: LocalDate?) = date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
}
