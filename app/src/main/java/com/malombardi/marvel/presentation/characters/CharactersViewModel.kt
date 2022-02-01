package com.malombardi.marvel.presentation.characters

import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.presentation.MarvelViewModel
import com.malombardi.marvel.presentation.characters.list.CharacterListUiState
import kotlinx.coroutines.flow.MutableStateFlow

class CharactersViewModel : MarvelViewModel() {

    val uiState = MutableStateFlow<CharactersActivityUiState>(CharactersActivityUiState.ListState)

    fun onCharacterSelected(character: MarvelCharacter){
        uiState.value = CharactersActivityUiState.DetailState(character)
    }
}