package com.malombardi.data

import com.malombardi.domain.datasources.RemoteDataSource
import com.malombardi.domain.models.MarvelCharacter
import com.malombardi.domain.models.MarvelComic
import com.malombardi.data.network.NetworkFactory
import com.malombardi.data.network.WebService
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