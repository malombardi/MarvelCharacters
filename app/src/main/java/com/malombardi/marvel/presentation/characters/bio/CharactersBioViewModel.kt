package com.malombardi.marvel.presentation.characters.bio

import com.malombardi.marvel.presentation.MarvelViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class CharactersBioViewModel: MarvelViewModel() {

    val uiState = MutableStateFlow<CharacterBioUiState>(CharacterBioUiState.InitialLoading)

    fun setUrl(link: String?){
        if (link.isNullOrBlank()){
            uiState.value = CharacterBioUiState.Error
        } else {
            uiState.value = CharacterBioUiState.Loading(link)
        }
    }

    fun onPageLoaded(){
        uiState.value = CharacterBioUiState.DataLoaded
    }
}