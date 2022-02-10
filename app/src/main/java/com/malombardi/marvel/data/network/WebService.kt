package com.malombardi.marvel.data.network

import com.google.gson.GsonBuilder
import com.malombardi.marvel.domain.Constants.BASE_URL
import com.malombardi.marvel.domain.Constants.DATE_FORMAT
import com.malombardi.marvel.data.network.responses.MarvelCharacterResponse
import com.malombardi.marvel.data.network.responses.MarvelComicResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebService {
    private val api: Api

    init {
        val gson = GsonBuilder()
            .setDateFormat(DATE_FORMAT)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
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
        return api.getCharacterById(characterId,options)
    }

    suspend fun searchCharacter(
        options: Map<String, String>,
        startWith: String
    ): MarvelCharacterResponse {
        return api.searchCharacters(startWith,options)
    }

    suspend fun getComics(options: Map<String, String>, characterId: String): MarvelComicResponse {
        return api.getComicForCharacterId(characterId,options)
    }
}
