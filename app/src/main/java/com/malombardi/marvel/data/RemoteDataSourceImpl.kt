package com.malombardi.marvel.data

import com.malombardi.marvel.domain.datasources.RemoteDataSource
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic
import com.malombardi.marvel.data.network.NetworkFactory
import com.malombardi.marvel.data.network.WebService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val webService: WebService) :
    RemoteDataSource {

    override suspend fun getCharacters(offset: Int): List<MarvelCharacter> {
        return webService.getCharacters(NetworkFactory.getNetworkOptions(offset, true))
            .toDomainCharacterList()
    }

    override suspend fun searchCharacters(startWith: String, offset: Int): List<MarvelCharacter> {
        return webService.searchCharacter(NetworkFactory.getNetworkOptions(offset, true), startWith)
            .toDomainCharacterList()
    }

    override suspend fun getComics(characterId: String, offset: Int): List<MarvelComic> {
        return webService.getComics(NetworkFactory.getNetworkOptions(offset, false), characterId)
            .toDomainComicList()
    }

}