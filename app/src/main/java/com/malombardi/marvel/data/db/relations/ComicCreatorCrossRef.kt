package com.malombardi.marvel.data.db.relations

import androidx.room.Entity

@Entity(primaryKeys = ["comic_id", "creator_id"])
data class ComicCreatorCrossRef (
    val comicId: Int,
    val creatorId: String
)
