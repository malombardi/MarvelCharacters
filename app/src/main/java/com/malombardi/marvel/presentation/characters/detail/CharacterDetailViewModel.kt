package com.malombardi.marvel.presentation.characters.detail

import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.usecases.GetComicsUseCase
import com.malombardi.marvel.presentation.MarvelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val getComicsUseCase: GetComicsUseCase) : MarvelViewModel() {

    lateinit var character: StateFlow<MarvelCharacter>

    fun setSelectedCharacter(marvelCharacter: MarvelCharacter) {
        character = MutableStateFlow(marvelCharacter)
    }

}