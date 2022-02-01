package com.malombardi.marvel.presentation.characters.list

import com.malombardi.marvel.domain.errors.ErrorEntity
import com.malombardi.marvel.domain.models.MarvelCharacter

sealed class CharacterListUiState {
    data class SuccessState(val data: List<MarvelCharacter>) : CharacterListUiState()
    data class ErrorState(val error: ErrorEntity) : CharacterListUiState()
    data class LoadingState(val spinner: Boolean): CharacterListUiState()
}