package com.malombardi.marvel.presentation.characters.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.malombardi.marvel.databinding.FragmentCharacterDetailBinding
import com.malombardi.marvel.databinding.FragmentCharacterListBinding
import com.malombardi.marvel.domain.models.MarvelCharacter

class CharacterDetailFragment constructor(private val character: MarvelCharacter) : Fragment() {

    private lateinit var binding: FragmentCharacterDetailBinding

    val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setSelectedCharacter(character)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val NAME = "CharacterDetailFragment"
    }
}