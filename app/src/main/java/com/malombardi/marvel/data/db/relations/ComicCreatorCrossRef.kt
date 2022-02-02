package com.malombardi.marvel.data.db.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["comic_id", "creator_id"])
data class ComicCreatorCrossRef (
    @ColumnInfo(name = "comic_id")
    val comicId: Int,
    @ColumnInfo(name = "creator_id")
    val creatorId: String
)
