package com.malombardi.marvel.data.db.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["character_id", "comic_id"])
data class CharacterComicCrossRef (
    @ColumnInfo(name = "character_id")
    val characterId: Int,
    @ColumnInfo(name = "comic_id")
    val comicId: Int
)
