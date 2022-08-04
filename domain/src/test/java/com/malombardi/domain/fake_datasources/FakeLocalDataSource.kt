package com.malombardi.domain.fake_datasources

import com.malombardi.domain.datasources.LocalDataSource
import com.malombardi.domain.fake_datasources.FakeDataSource.Companion.MAX_RESULTS
import com.malombardi.domain.fake_datasources.FakeDataSource.Companion.getFakeMarvelCharacter
import com.malombardi.domain.fake_datasources.FakeDataSource.Companion.getFakeMarvelComic
import com.malombardi.domain.models.MarvelCharacter
import com.malombardi.domain.models.MarvelComic
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource : LocalDataSource {
    override suspend fun charactersSize(): Int {
        return MAX_RESULTS
    }

    override suspend fun searchSize(startWith: String): Int {
        return MAX_RESULTS
    }

    override suspend fun comicsSize(characterId: String): Int {
        return MAX_RESULTS
    }

    override suspend fun saveCharacters(characters: List<MarvelCharacter>) {
        // Do Nothing
    }

    override suspend fun saveSearch(characters: List<MarvelCharacter>) {
        // Do Nothing
    }

    override suspend fun saveComics(characterId: String, comics: List<MarvelComic>) {
        // Do Nothing
    }

    override fun getCharacters() = flow {
        emit(
            getFakeMarvelCharacter()
        )
    }

    override fun searchCharacters(startWith: String) = flow {
        emit(
            getFakeMarvelCharacter().reversed()
        )
    }

    override fun getComics(characterId: String) = flow {
        emit(
            getFakeMarvelComic()
        )
    }
}
