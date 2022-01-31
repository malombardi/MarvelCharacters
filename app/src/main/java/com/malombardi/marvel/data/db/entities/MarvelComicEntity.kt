package com.malombardi.marvel.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.malombardi.marvel.domain.Constants
import com.malombardi.marvel.domain.models.Creator

@Entity
data class MarvelComicEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "comic_id") val id: Int? = Constants.UNKNOWN_ID,
    val digitalId: Int? = Constants.UNKNOWN_ID,
    val description: String? = "",
    val thumbnail: String? = "",
    val creators: List<MarvelCreatorEntity>? = null,
    val title: String? = ""
)
