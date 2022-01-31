package com.malombardi.marvel.domain.datasources

import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelCharacterResponse
import com.malombardi.marvel.domain.models.MarvelComic
import com.malombardi.marvel.domain.models.MarvelComicResponse
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun charactersSize(): Int

    suspend fun searchSize(): Int

    suspend fun comicsSize(): Int

    suspend fun saveCharacters(characters: MarvelCharacterResponse)

    suspend fun saveSearch(characters: MarvelCharacterResponse)

    suspend fun saveComics(comics: MarvelComicResponse)

    fun getCharacters(): Flow<List<MarvelCharacter>>

    fun searchCharacters(startWith: String): Flow<List<MarvelCharacter>>

    fun getComics(characterId: String): Flow<List<MarvelComic>>
}
