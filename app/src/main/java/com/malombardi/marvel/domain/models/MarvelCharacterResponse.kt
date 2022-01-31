package com.malombardi.marvel.domain.models

data class MarvelCharacterResponse(
    val data: CharacterData,
)

data class CharacterData(
    val results: List<MarvelCharacter>
)
