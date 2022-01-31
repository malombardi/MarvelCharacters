package com.malombardi.marvel.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MarvelCreatorEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "creator_id")
    val resourceURI: String?,
    val name: String?,
    val role: String?
)
