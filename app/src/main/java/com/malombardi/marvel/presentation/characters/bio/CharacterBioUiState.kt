package com.malombardi.marvel.presentation.characters.bio

sealed class CharacterBioUiState {
    data class Loading(val link: String) : CharacterBioUiState()
    object InitialLoading : CharacterBioUiState()
    object Error : CharacterBioUiState()
    object DataLoaded : CharacterBioUiState()
}