package com.malombardi.marvel.domain.repository.network

import com.google.gson.GsonBuilder
import com.malombardi.marvel.domain.repository.network.responses.MarvelCharacterResponse
import com.malombardi.marvel.domain.repository.network.responses.MarvelComicResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebService {
    private val api: Api

    init {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com:443/v1/public/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        api = retrofit.create(Api::class.java)
    }

    suspend fun getCharacters(options: Map<String, String>): MarvelCharacterResponse {
        return api.getCharacters(options)
    }

    suspend fun getCharacterDetail(
        options: Map<String, String>,
        characterId: String
    ): MarvelCharacterResponse {
        return api.getCharacterById(options, characterId)
    }

    suspend fun searchCharacter(
        options: Map<String, String>,
        startWith: String
    ): MarvelCharacterResponse {
        return api.searchCharacters(options, startWith)
    }

    suspend fun getComics(options: Map<String, String>, characterId: String): MarvelComicResponse {
        return api.getComicForCharacterId(options, characterId)
    }
}
