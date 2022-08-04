package com.malombardi.marvel.presentation.characters

import com.malombardi.domain.errors.ErrorEntity
import com.malombardi.domain.models.MarvelCharacter

sealed class CharactersActivityUiState {
    object ListState : CharactersActivityUiState()
    data class DetailState(val character: MarvelCharacter) : CharactersActivityUiState()
    data class ErrorState(val error: ErrorEntity.InternalError): CharactersActivityUiState()
    data class BioState(val link: String): CharactersActivityUiState()
    data class ComicsState(val characterId: Int): CharactersActivityUiState()
}
