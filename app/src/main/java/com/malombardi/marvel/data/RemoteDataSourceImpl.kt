package com.malombardi.marvel.data

import com.malombardi.marvel.domain.datasources.RemoteDataSource
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic
import com.malombardi.marvel.domain.repository.network.NetworkFactory
import com.malombardi.marvel.domain.repository.network.WebService
import javax.inject.Inject

class RemoteDataSourceImpl (private val webService: WebService = WebService()) :
    RemoteDataSource {

    override suspend fun getCharacters(offset: Int): List<MarvelCharacter> {
        return webService.getCharacters(NetworkFactory.getNetworkOptions(offset))
            .toDomainCharacterList()
    }

    override suspend fun searchCharacters(startWith: String, offset: Int): List<MarvelCharacter> {
        return webService.searchCharacter(NetworkFactory.getNetworkOptions(offset), startWith)
            .toDomainCharacterList()
    }

    override suspend fun getComics(characterId: String, offset: Int): List<MarvelComic> {
        return webService.getComics(NetworkFactory.getNetworkOptions(offset), characterId)
            .toDomainComicList()
    }

    companion object {
        private var instance: RemoteDataSourceImpl? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: RemoteDataSourceImpl().also { instance = it }
        }
    }
}