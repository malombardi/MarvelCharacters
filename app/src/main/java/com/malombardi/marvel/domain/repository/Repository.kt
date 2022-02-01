package com.malombardi.marvel.domain.repository

import com.malombardi.marvel.domain.Constants.PAGE_SIZE
import com.malombardi.marvel.domain.Constants.PAGE_THRESHOLD
import com.malombardi.marvel.domain.ResponseWrapper
import com.malombardi.marvel.domain.datasources.LocalDataSource
import com.malombardi.marvel.domain.datasources.RemoteDataSource
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

/**
 * The repository will get data from the DB if available and fetch from network.
 */
class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    fun getCharacters(): Flow<List<MarvelCharacter>> = localDataSource.getCharacters()

    suspend fun checkCharactersRequireNewPage(lastVisible: Int) {
        val size = localDataSource.charactersSize()
        if (lastVisible >= size - PAGE_THRESHOLD) {
            val offset = if(lastVisible == 0) 0 else {(size / PAGE_SIZE) * PAGE_SIZE}
            val newCharacters = withTimeout(5_000) { remoteDataSource.getCharacters(offset) }
            localDataSource.saveCharacters(newCharacters)
        }
    }

    fun searchCharacters(startWith: String): Flow<List<MarvelCharacter>> =
        localDataSource.searchCharacters(startWith)

    suspend fun checkSearchRequireNewPage(startWith: String, lastVisible: Int) {
        val size = localDataSource.searchSize(startWith)
        if (lastVisible >= size - PAGE_THRESHOLD) {
            val offset = size / PAGE_SIZE + 1
            val newCharacters =
                withTimeout(5_000) { remoteDataSource.searchCharacters(startWith, offset) }
            localDataSource.saveSearch(newCharacters)
        }
    }

    fun getCharacterComics(characterId: String): Flow<List<MarvelComic>> =
        localDataSource.getComics(characterId)

    suspend fun checkComicsRequireNewPage(characterId: String, lastVisible: Int) {
        val size = localDataSource.comicsSize(characterId)
        if (lastVisible >= size - PAGE_THRESHOLD) {
            val offset = size / PAGE_SIZE + 1
            val newComics = withTimeout(5_000) { remoteDataSource.getComics(characterId, offset) }
            localDataSource.saveComics(characterId, newComics)
        }
    }
}
