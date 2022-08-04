package com.malombardi.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.malombardi.domain.Constants

@Entity
data class MarvelCreatorEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "creator_id")
    val resourceURI: String,
    val name: String?,
    val role: String?
)
