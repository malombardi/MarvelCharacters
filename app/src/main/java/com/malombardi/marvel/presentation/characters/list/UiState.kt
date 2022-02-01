package com.malombardi.marvel.presentation.characters.list

import com.malombardi.marvel.domain.errors.ErrorEntity
import com.malombardi.marvel.domain.models.MarvelCharacter

sealed class UiState {
    data class SuccessState(val data: List<MarvelCharacter>) : UiState()
    data class ErrorState(val error: ErrorEntity) : UiState()
    data class LoadingState(val spinner: Boolean): UiState()
}