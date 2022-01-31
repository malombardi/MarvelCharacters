package com.malombardi.marvel.domain.fake_datasources

import com.malombardi.marvel.domain.datasources.LocalDataSource
import com.malombardi.marvel.domain.fake_datasources.FakeDataSource.Companion.MAX_RESULTS
import com.malombardi.marvel.domain.fake_datasources.FakeDataSource.Companion.getFakeMarvelCharacter
import com.malombardi.marvel.domain.fake_datasources.FakeDataSource.Companion.getFakeMarvelComic
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource : LocalDataSource {
    override suspend fun charactersSize(): Int {
        return MAX_RESULTS
    }

    override suspend fun searchSize(): Int {
        return MAX_RESULTS
    }

    override suspend fun comicsSize(): Int {
        return MAX_RESULTS
    }

    override suspend fun saveCharacters(characters: List<MarvelCharacter>) {
        // Do Nothing
    }

    override suspend fun saveSearch(characters: List<MarvelCharacter>) {
        // Do Nothing
    }

    override suspend fun saveComics(comics: List<MarvelComic>) {
        // Do Nothing
    }

    override fun getCharacters() = flow {
        emit(
            getFakeMarvelCharacter()
        )
    }

    override fun searchCharacters(startWith: String) = flow {
        emit(
            getFakeMarvelCharacter()
        )
    }

    override fun getComics(characterId: String) = flow {
        emit(
            getFakeMarvelComic()
        )
    }
}
