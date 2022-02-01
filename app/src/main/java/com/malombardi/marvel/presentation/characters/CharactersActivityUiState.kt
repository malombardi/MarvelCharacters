package com.malombardi.marvel.presentation.characters

import com.malombardi.marvel.domain.models.MarvelCharacter

sealed class CharactersActivityUiState {
    object ListState : CharactersActivityUiState()
    data class DetailState(val character: MarvelCharacter) : CharactersActivityUiState()
}