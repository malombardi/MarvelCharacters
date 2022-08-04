package com.malombardi.marvel.presentation.characters.detail

import com.malombardi.domain.models.MarvelCharacter
import com.malombardi.marvel.presentation.MarvelViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class CharacterDetailViewModel : MarvelViewModel() {

    val uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)

    fun setSelectedCharacter(marvelCharacter: MarvelCharacter?) {
        if (marvelCharacter?.id == null) {
            uiState.value = CharacterDetailUiState.Error
        } else {
            uiState.value = CharacterDetailUiState.Success(marvelCharacter)
        }
    }
}
