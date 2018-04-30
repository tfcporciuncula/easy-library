package com.blinkist.easylibrary.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
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
) : Librariable

private val String.time
    get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this).time
