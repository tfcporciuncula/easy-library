package com.blinkist.easylibrary.model

import android.annotation.SuppressLint
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.text.SimpleDateFormat

@Entity(tableName = "books")
data class Book(

    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "published_date")
    val publishedDate: String,

    @ColumnInfo(name = "published_date_value")
    val publishedDateValue: Long = publishedDate.time,

    val title: String,
    val authors: String,
    val thumbnail: String,
    val url: String
)

private val String.time
    @SuppressLint("SimpleDateFormat")
    get() = SimpleDateFormat("yyyy-MM-dd").parse(this).time
