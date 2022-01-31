package com.malombardi.marvel.domain.fake_datasources

import com.malombardi.marvel.domain.datasources.RemoteDataSource
import com.malombardi.marvel.domain.fake_datasources.FakeDataSource.Companion.getFakeCharacterData
import com.malombardi.marvel.domain.fake_datasources.FakeDataSource.Companion.getFakeComicData
import com.malombardi.marvel.domain.models.MarvelCharacterResponse
import com.malombardi.marvel.domain.models.MarvelComicResponse

class FakeRemoteDataSource : RemoteDataSource {
    override suspend fun getCharacters(offset: Int): MarvelCharacterResponse {
        return MarvelCharacterResponse(getFakeCharacterData())
    }

    override suspend fun searchCharacters(startWith: String, offset: Int): MarvelCharacterResponse {
        return MarvelCharacterResponse(getFakeCharacterData())
    }

    override suspend fun getComics(characterId: String, offset: Int): MarvelComicResponse {
        return MarvelComicResponse(getFakeComicData())
    }
}
