package com.blinkist.easylibrary.model

import android.annotation.SuppressLint
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.blinkist.easylibrary.library.Librariable
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "books")
data class Book(

    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "published_date")
    val publishedDate: String,

    @ColumnInfo(name = "published_date_time")
    val publishedDateTime: Long = publishedDate.time,

    val title: String,
    val authors: String,
    val thumbnail: String,
    val url: String
) : Librariable {

    @Ignore
    private val calendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
        time = Date(publishedDateTime)
    }

    @Ignore
    val year = calendar.get(Calendar.YEAR)

    @Ignore
    val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

    fun belongsTo(weekSection: WeekSection): Boolean {
        return year == weekSection.year && weekOfYear == weekSection.weekOfYear
    }
}

private val String.time
    @SuppressLint("SimpleDateFormat")
    get() = SimpleDateFormat("yyyy-MM-dd").parse(this).time
