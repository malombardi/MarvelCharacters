package com.malombardi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.malombardi.data.db.dao.MarvelDao
import com.malombardi.data.db.entities.MarvelCharacterEntity
import com.malombardi.data.db.entities.MarvelComicEntity
import com.malombardi.data.db.entities.MarvelCreatorEntity
import com.malombardi.data.db.relations.CharacterComicCrossRef
import com.malombardi.data.db.relations.ComicCreatorCrossRef
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
