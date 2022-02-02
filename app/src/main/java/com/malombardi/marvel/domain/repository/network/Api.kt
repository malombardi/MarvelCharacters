package com.malombardi.marvel.domain.repository.network

import com.malombardi.marvel.domain.repository.network.responses.MarvelCharacterResponse
import com.malombardi.marvel.domain.repository.network.responses.MarvelComicResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {

    @GET("characters")
    suspend fun getCharacters(@QueryMap params: Map<String, String>): MarvelCharacterResponse

    @GET("characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: String,
        @QueryMap params: Map<String, String>
    ): MarvelCharacterResponse

    @GET("characters/{characterId}/comics")
    suspend fun getComicForCharacterId(
        @Path("characterId") characterId: String,
        @QueryMap params: Map<String, String>
    ): MarvelComicResponse

    @GET("characters")
    suspend fun searchCharacters(
        @Query("nameStartsWith") nameStartsWith: String,
        @QueryMap params: Map<String, String>
    ): MarvelCharacterResponse
}
