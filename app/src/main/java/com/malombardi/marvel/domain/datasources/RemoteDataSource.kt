package com.malombardi.marvel.domain.datasources

import com.malombardi.marvel.domain.models.MarvelCharacterResponse
import com.malombardi.marvel.domain.models.MarvelComicResponse

interface RemoteDataSource {
    suspend fun getCharacters(offset: Int): MarvelCharacterResponse

    suspend fun searchCharacters(startWith: String, offset: Int): MarvelCharacterResponse

    suspend fun getComics(characterId: String, offset: Int): MarvelComicResponse
}
