package com.malombardi.data.db.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.malombardi.data.db.entities.MarvelComicEntity
import com.malombardi.data.db.entities.MarvelCreatorEntity

data class ComicWithCreators(
    @Embedded val marvelComic: MarvelComicEntity,
    @Relation(
        parentColumn = "comic_id",
        entityColumn = "creator_id",
        associateBy = Junction(
            ComicCreatorCrossRef::class,
            parentColumn = "comic_id",
            entityColumn = "creator_id"
        )
    )
    val creators: List<MarvelCreatorEntity>
)
