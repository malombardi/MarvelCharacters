package com.malombardi.marvel.presentation.characters.list

import com.malombardi.domain.errors.ErrorEntity
import com.malombardi.domain.models.MarvelCharacter

sealed class CharacterListUiState {
    data class SuccessState(val data: List<MarvelCharacter>) : CharacterListUiState()
    data class ErrorState(val error: ErrorEntity) : CharacterListUiState()
    data class LoadingState(val spinner: Boolean): CharacterListUiState()
}