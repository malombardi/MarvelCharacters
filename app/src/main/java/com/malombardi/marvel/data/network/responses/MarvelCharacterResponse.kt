package com.malombardi.marvel.data.network.responses

data class MarvelCharacterResponse(
    val data: CharacterData,
)

data class CharacterData(
    val results: List<MarvelCharacter>
)
