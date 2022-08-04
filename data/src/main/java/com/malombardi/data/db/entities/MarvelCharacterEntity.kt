package com.malombardi.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.malombardi.domain.Constants

@Entity
data class MarvelCharacterEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "character_id")
    val id: Int? = Constants.UNKNOWN_ID,
    val name: String? = "",
    val description: String? = "",
    val thumbnail: String? = "",
    @ColumnInfo(name = "bio_link")val bioLink: String? = "",
    @ColumnInfo(name = "comics_count")val comicsCount: Int? = Constants.COMICS_EMPTY
)
