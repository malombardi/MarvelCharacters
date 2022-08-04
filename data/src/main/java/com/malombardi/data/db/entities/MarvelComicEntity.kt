package com.malombardi.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.malombardi.data.db.Converter
import com.malombardi.domain.Constants

@Entity
data class MarvelComicEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "comic_id") val id: Int? = Constants.UNKNOWN_ID,
    val digitalId: Int? = Constants.UNKNOWN_ID,
    val description: String? = "",
    val thumbnail: String? = "",
    @TypeConverters(Converter::class)
    val creators: List<MarvelCreatorEntity>? = null,
    val title: String? = ""
)
