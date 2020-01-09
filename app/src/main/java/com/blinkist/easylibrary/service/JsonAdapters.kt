package com.blinkist.easylibrary.service

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class OffsetDateTimeAdapter {

  @ToJson fun toJson(value: OffsetDateTime): String = value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

  @FromJson fun fromJson(value: String): OffsetDateTime = OffsetDateTime.parse(value)
}

class LocalDateAdapter {

  @ToJson fun toJson(value: LocalDate): String = value.format(DateTimeFormatter.ISO_LOCAL_DATE)

  @FromJson fun fromJson(value: String): LocalDate = LocalDate.parse(value)
}
