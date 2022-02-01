package com.malombardi.marvel.presentation.characters

import com.malombardi.marvel.domain.Constants
import com.malombardi.marvel.domain.errors.ErrorEntity
import com.malombardi.marvel.domain.errors.ErrorHandler
import com.malombardi.marvel.domain.errors.ErrorHandler.Companion.UNKNOWN_ERROR_CODE
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.presentation.MarvelViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CharactersViewModel : MarvelViewModel() {

    private lateinit var character : MarvelCharacter
    val uiState = MutableStateFlow<CharactersActivityUiState>(CharactersActivityUiState.ListState)

    fun onItemSelected(marvelCharacter: MarvelCharacter) {
        character = marvelCharacter
        uiState.value = CharactersActivityUiState.DetailState(marvelCharacter)
    }

    fun onActivityReturned(){
        uiState.value = CharactersActivityUiState.DetailState(character)
    }

    fun onReturnedWithError(){
        uiState.value = CharactersActivityUiState.ErrorState(
            ErrorEntity.InternalError(UNKNOWN_ERROR_CODE)
        )
    }

    fun onBioSelected(url: String?) {
        if (url.isNullOrBlank()) {
            uiState.value = CharactersActivityUiState.ErrorState(
                ErrorEntity.InternalError(UNKNOWN_ERROR_CODE)
            )
        } else {
            uiState.value = CharactersActivityUiState.BioState(url)
        }
    }

    fun onComicsSelected(id: Int?) {
        if (id == null || id == Constants.UNKNOWN_ID) {
            uiState.value = CharactersActivityUiState.ErrorState(
                ErrorEntity.InternalError(UNKNOWN_ERROR_CODE)
            )
        } else {
            uiState.value = CharactersActivityUiState.ComicsState(id)
        }
    }

    fun onDetailReturn(){
        uiState.value = CharactersActivityUiState.ListState
    }
}
