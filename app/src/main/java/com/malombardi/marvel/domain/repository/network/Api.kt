package com.malombardi.marvel.domain.repository.network

import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface Api {

    @GET("characters")
    suspend fun getCharacters(@QueryMap params: Map<String, String>): List<MarvelCharacter>

    @GET("characters/{characterId}")
    suspend fun getCharacterById(
        @QueryMap params: Map<String, String>,
        @Path("characterId") characterId: String
    ): MarvelCharacter

    @GET("characters/{characterId}/comics")
    suspend fun getComicForCharacterId(
        @QueryMap params: Map<String, String>,
        @Path("characterId") characterId: String
    ): List<MarvelComic>

    @GET("characters")
    suspend fun searchCharacters(
        @QueryMap params: Map<String, String>,
        @Path("nameStartsWith") nameStartsWith: String
    ): List<MarvelCharacter>
}
