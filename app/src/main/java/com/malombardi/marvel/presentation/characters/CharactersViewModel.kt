package com.malombardi.marvel.presentation.characters

import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.presentation.MarvelViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class CharactersViewModel : MarvelViewModel() {

    val uiState = MutableStateFlow<CharactersActivityUiState>(CharactersActivityUiState.ListState)

    fun onItemSelected(marvelCharacter: MarvelCharacter) {
        uiState.value = CharactersActivityUiState.DetailState(marvelCharacter)
    }
}