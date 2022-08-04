package com.malombardi.domain.fake_datasources

import com.malombardi.domain.datasources.RemoteDataSource
import com.malombardi.domain.fake_datasources.FakeDataSource.Companion.getFakeMarvelCharacter
import com.malombardi.domain.fake_datasources.FakeDataSource.Companion.getFakeMarvelComic
import com.malombardi.domain.models.MarvelCharacter
import com.malombardi.domain.models.MarvelComic

class FakeRemoteDataSource : RemoteDataSource {
    override suspend fun getCharacters(offset: Int): List<MarvelCharacter> {
        return getFakeMarvelCharacter()
    }

    override suspend fun searchCharacters(startWith: String, offset: Int): List<MarvelCharacter> {
        return getFakeMarvelCharacter()
    }

    override suspend fun getComics(characterId: String, offset: Int): List<MarvelComic> {
        return getFakeMarvelComic()
    }
}
