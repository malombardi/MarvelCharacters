package com.malombardi.marvel.domain.datasources

import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic

interface RemoteDataSource {
    suspend fun getCharacters(offset: Int): List<MarvelCharacter>

    suspend fun searchCharacters(startWith: String, offset: Int): List<MarvelCharacter>

    suspend fun getComics(characterId: String, offset: Int): List<MarvelComic>
}
