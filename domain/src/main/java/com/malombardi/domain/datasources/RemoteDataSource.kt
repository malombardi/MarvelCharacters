package com.malombardi.domain.datasources

import com.malombardi.domain.models.MarvelCharacter
import com.malombardi.domain.models.MarvelComic

interface RemoteDataSource {
    suspend fun getCharacters(offset: Int): List<MarvelCharacter>

    suspend fun searchCharacters(startWith: String, offset: Int): List<MarvelCharacter>

    suspend fun getComics(characterId: String, offset: Int): List<MarvelComic>
}
