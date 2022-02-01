package com.malombardi.marvel.presentation.characters.detail

import androidx.fragment.app.Fragment
import com.malombardi.marvel.domain.models.MarvelCharacter

class CharacterDetailFragment constructor(private val character: MarvelCharacter): Fragment() {

    companion object{
        const val NAME = "CharacterDetailFragment"
    }
}