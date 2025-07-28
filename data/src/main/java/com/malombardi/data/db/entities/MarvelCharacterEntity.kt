package com.malombardi.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.malombardi.domain.Constants

@Entity
data class MarvelCharacterEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "character_id")
    var id: Int? = Constants.UNKNOWN_ID,
    var name: String? = "",
    var description: String? = "",
    var thumbnail: String? = "",
    @ColumnInfo(name = "bio_link")var bioLink: String? = "",
    @ColumnInfo(name = "comics_count")var comicsCount: Int? = Constants.COMICS_EMPTY,
    @ColumnInfo(name = "is_fav")var isFav: Boolean = false
)
