package com.malombardi.marvel.presentation.characters.detail

import com.malombardi.marvel.domain.models.MarvelCharacter

sealed class CharacterDetailUiState {
    data class Success(val character: MarvelCharacter) : CharacterDetailUiState()
    object Loading : CharacterDetailUiState()
    object Error : CharacterDetailUiState()
}
