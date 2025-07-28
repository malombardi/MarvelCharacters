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
    @ColumnInfo(name = "comic_id") var id: Int? = Constants.UNKNOWN_ID,
    var digitalId: Int? = Constants.UNKNOWN_ID,
    var description: String? = "",
    var thumbnail: String? = "",
    @TypeConverters(Converter::class)
    var creators: List<MarvelCreatorEntity>? = null,
    var title: String? = ""
)
