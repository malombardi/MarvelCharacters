package com.malombardi.marvel.domain.repository.network

import com.malombardi.marvel.domain.models.MarvelCharacterResponse
import com.malombardi.marvel.domain.models.MarvelComicResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface Api {

    @GET("characters")
    suspend fun getCharacters(@QueryMap params: Map<String, String>): MarvelCharacterResponse

    @GET("characters/{characterId}")
    suspend fun getCharacterById(
        @QueryMap params: Map<String, String>,
        @Path("characterId") characterId: String
    ): MarvelCharacterResponse

    @GET("characters/{characterId}/comics")
    suspend fun getComicForCharacterId(
        @QueryMap params: Map<String, String>,
        @Path("characterId") characterId: String
    ): MarvelComicResponse

    @GET("characters")
    suspend fun searchCharacters(
        @QueryMap params: Map<String, String>,
        @Path("nameStartsWith") nameStartsWith: String
    ): MarvelCharacterResponse
}
