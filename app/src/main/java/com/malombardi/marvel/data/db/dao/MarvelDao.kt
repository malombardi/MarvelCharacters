package com.malombardi.marvel.data.db.dao

import androidx.room.*
import com.malombardi.marvel.data.db.entities.MarvelCharacterEntity
import com.malombardi.marvel.data.db.entities.MarvelComicEntity
import com.malombardi.marvel.data.db.relations.CharacterComicCrossRef
import com.malombardi.marvel.data.db.relations.CharacterWithComics
import com.malombardi.marvel.data.db.relations.ComicCreatorCrossRef
import com.malombardi.marvel.data.db.relations.ComicWithCreators
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelDao {

    @Query("SELECT COUNT(character_id) FROM marvelcharacterentity")
    suspend fun charactersCount(): Int

    @Query("SELECT COUNT(character_id) FROM marvelcharacterentity WHERE character_id LIKE '%' || :startsWith")
    suspend fun searchCount(startsWith: String): Int

    @Query("SELECT COUNT(character_id) FROM marvelcharacterentity WHERE character_id = :characterId")
    suspend fun comicsCount(characterId: String): Int

    @Query("SELECT * FROM marvelcharacterentity ORDER BY name ASC")
    fun getCharacters(): Flow<List<MarvelCharacterEntity>>

    @Query("SELECT * FROM marvelcharacterentity WHERE character_id LIKE '%' || :startsWith ORDER BY name ASC")
    fun getCharactersWithName(startsWith: String): Flow<List<MarvelCharacterEntity>>

    @Query("SELECT * FROM marvelcharacterentity WHERE character_id = :characterId")
    fun getComicsForCharacter(characterId: String): List<CharacterWithComics>

    @Query("SELECT * FROM marvelcomicentity WHERE comic_id = :comicId")
    fun getCreatorsForComic(comicId: String): Flow<List<ComicWithCreators>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCharacter(charactersEntity: List<MarvelCharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveComic(comicsEntity: List<MarvelComicEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCharacterComic(join: CharacterComicCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveComicCreator(join: ComicCreatorCrossRef)
}
