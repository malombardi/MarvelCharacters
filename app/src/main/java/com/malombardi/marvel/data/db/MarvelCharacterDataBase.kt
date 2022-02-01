package com.malombardi.marvel.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.malombardi.marvel.data.db.dao.MarvelDao
import com.malombardi.marvel.data.db.entities.MarvelCharacterEntity
import com.malombardi.marvel.data.db.entities.MarvelComicEntity
import com.malombardi.marvel.data.db.entities.MarvelCreatorEntity
import com.malombardi.marvel.data.db.relations.CharacterComicCrossRef
import com.malombardi.marvel.data.db.relations.ComicCreatorCrossRef
import javax.inject.Singleton

@Database(
    entities = [MarvelCharacterEntity::class,
        MarvelComicEntity::class,
        MarvelCreatorEntity::class,
        CharacterComicCrossRef::class,
        ComicCreatorCrossRef::class
    ], version = 1
)
@TypeConverters(Converter::class)
@Singleton
abstract class MarvelCharacterDataBase : RoomDatabase() {
    abstract fun marvelDao(): MarvelDao

    companion object{
        const val DB_NAME = "marvel_db"
    }
}
