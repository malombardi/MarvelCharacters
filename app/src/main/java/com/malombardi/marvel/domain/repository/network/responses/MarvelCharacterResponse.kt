package com.malombardi.marvel.domain.repository.network.responses

data class MarvelCharacterResponse(
    val data: CharacterData,
)

data class CharacterData(
    val results: List<MarvelCharacter>
)
