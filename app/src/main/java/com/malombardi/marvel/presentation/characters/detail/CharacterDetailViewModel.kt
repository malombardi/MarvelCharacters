package com.malombardi.marvel.presentation.characters.detail

import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.usecases.GetComicsUseCase
import com.malombardi.marvel.presentation.MarvelViewModel
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(private val getComicsUseCase: GetComicsUseCase) : MarvelViewModel() {

    lateinit var character: MarvelCharacter

    fun setSelectedCharacter(marvelCharacter: MarvelCharacter) {
        character = marvelCharacter
    }


}