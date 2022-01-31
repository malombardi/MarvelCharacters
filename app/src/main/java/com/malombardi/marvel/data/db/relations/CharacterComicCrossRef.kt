package com.malombardi.marvel.data.db.relations

import androidx.room.Entity

@Entity(primaryKeys = ["character_id", "comic_id"])
data class CharacterComicCrossRef (
    val characterId: Int,
    val comicId: Int
)
