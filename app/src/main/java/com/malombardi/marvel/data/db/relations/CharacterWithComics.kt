package com.malombardi.marvel.data.db.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.malombardi.marvel.data.db.entities.MarvelCharacterEntity
import com.malombardi.marvel.data.db.entities.MarvelComicEntity

data class CharacterWithComics(
    @Embedded val marvelCharacter: MarvelCharacterEntity,
    @Relation(
        parentColumn = "character_id",
        entityColumn = "comic_id",
        associateBy = Junction(
            CharacterComicCrossRef::class,
            parentColumn = "character_id",
            entityColumn = "comic_id"
        )
    )
    val comics: List<MarvelComicEntity>? = null
)