package com.malombardi.marvel.domain.fake_datasources

import com.malombardi.marvel.domain.datasources.LocalDataSource
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic
import kotlinx.coroutines.flow.flow

open class FakeLocalDataSource : LocalDataSource {
    override suspend fun charactersSize(): Int {
        return 5
    }

    override suspend fun searchSize(): Int {
        return 5
    }

    override suspend fun comicsSize(): Int {
        return 5
    }

    override suspend fun saveCharacters(characters: List<MarvelCharacter>) {
        // Do nothing
    }

    override suspend fun saveSearch(characters: List<MarvelCharacter>) {
        // Do nothing
    }

    override suspend fun saveComics(comics: List<MarvelComic>) {
        // Do nothing
    }

    override fun getCharacters() = flow {
        emit(
            listOf(
                MarvelCharacter("1"),
                MarvelCharacter("2"),
                MarvelCharacter("3"),
                MarvelCharacter("4"),
                MarvelCharacter("5")
            )
        )
    }

    override fun searchCharacters(startWith: String) = flow {
        emit(
            listOf(
                MarvelCharacter("1"),
                MarvelCharacter("2"),
                MarvelCharacter("3"),
                MarvelCharacter("4"),
                MarvelCharacter("5")
            )
        )
    }

    override fun getComics(characterId: String) = flow {
        emit(
            listOf(
                MarvelComic("1"),
                MarvelComic("2"),
                MarvelComic("3"),
                MarvelComic("4"),
                MarvelComic("5")
            )
        )
    }
}
