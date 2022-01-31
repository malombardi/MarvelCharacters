package com.malombardi.marvel.domain.fake_datasources

import com.malombardi.marvel.domain.datasources.LocalDataSource
import com.malombardi.marvel.domain.fake_datasources.FakeDataSource.Companion.MAX_RESULTS
import com.malombardi.marvel.domain.fake_datasources.FakeDataSource.Companion.getFakeCharacterData
import com.malombardi.marvel.domain.fake_datasources.FakeDataSource.Companion.getFakeComicData
import com.malombardi.marvel.domain.models.*
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

    override suspend fun saveCharacters(characters: MarvelCharacterResponse) {
        // Do Nothing
    }

    override suspend fun saveSearch(characters: MarvelCharacterResponse) {
        // Do Nothing
    }

    override suspend fun saveComics(comics: MarvelComicResponse) {
        // Do Nothing
    }

    override fun getCharacters() = flow {
        val fakeData = getFakeCharacterData()
        emit(
            fakeData.results
        )
    }

    override fun searchCharacters(startWith: String) = flow {
        val fakeData = getFakeCharacterData()
        emit(
            fakeData.results
        )
    }

    override fun getComics(characterId: String) = flow {
        val fakeData = getFakeComicData()
        emit(
            fakeData.results
        )
    }
}
