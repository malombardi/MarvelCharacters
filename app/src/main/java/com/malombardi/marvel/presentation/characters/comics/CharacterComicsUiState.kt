package com.malombardi.marvel.presentation.characters.comics

import com.malombardi.domain.errors.ErrorEntity
import com.malombardi.domain.models.MarvelComic

sealed class CharacterComicsUiState {
    data class Error(val error: ErrorEntity) : CharacterComicsUiState()
    data class Success(val data: List<MarvelComic>) : CharacterComicsUiState()
    object Loading : CharacterComicsUiState()
}
