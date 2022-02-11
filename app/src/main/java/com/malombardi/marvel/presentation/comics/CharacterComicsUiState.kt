package com.malombardi.marvel.presentation.comics

import com.malombardi.marvel.domain.errors.ErrorEntity
import com.malombardi.marvel.domain.models.MarvelComic

sealed class CharacterComicsUiState {
    data class Error(val error: ErrorEntity) : CharacterComicsUiState()
    data class Success(val data: List<MarvelComic>) : CharacterComicsUiState()
    object Loading : CharacterComicsUiState()
}
