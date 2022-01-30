package com.malombardi.marvel.domain.fake_datasources

import com.malombardi.marvel.domain.datasources.RemoteDataSource
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic

class FakeRemoteDataSource : RemoteDataSource {
    override suspend fun getCharacters(offset: Int): List<MarvelCharacter> {
        return listOf(
            MarvelCharacter("1"),
            MarvelCharacter("2"),
            MarvelCharacter("3"),
            MarvelCharacter("4"),
            MarvelCharacter("5")
        )
    }

    override suspend fun searchCharacters(startWith: String, offset: Int): List<MarvelCharacter> {
        return listOf(
            MarvelCharacter("1"),
            MarvelCharacter("2"),
            MarvelCharacter("3"),
            MarvelCharacter("4"),
            MarvelCharacter("5")
        )
    }

    override suspend fun getComics(characterId: String, offset: Int): List<MarvelComic> {
        return listOf(
            MarvelComic("1"),
            MarvelComic("2"),
            MarvelComic("3"),
            MarvelComic("4"),
            MarvelComic("5")
        )
    }
}