package com.malombardi.marvel.domain.datasources

import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun charactersSize(): Int

    suspend fun searchSize(startWith: String): Int

    suspend fun comicsSize(characterId: String): Int

    suspend fun saveCharacters(characters: List<MarvelCharacter>)

    suspend fun saveSearch(characters: List<MarvelCharacter>)

    suspend fun saveComics(characterId: String, comics: List<MarvelComic>)

    fun getCharacters(): Flow<List<MarvelCharacter>>

    fun searchCharacters(startWith: String): Flow<List<MarvelCharacter>>

    fun getComics(characterId: String): Flow<List<MarvelComic>>
}
