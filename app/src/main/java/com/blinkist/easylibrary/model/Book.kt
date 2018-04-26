package com.blinkist.easylibrary.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "books")
data class Book(

    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "published_data")
    val publishedDate: String,

    val title: String,
    val authors: String,
    val thumbnail: String,
    val url: String
)
