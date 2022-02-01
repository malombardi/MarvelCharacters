package com.malombardi.marvel.presentation.characters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.malombardi.marvel.R
import com.malombardi.marvel.databinding.ActivityCharactersBinding
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.presentation.characters.detail.CharacterDetailFragment
import com.malombardi.marvel.presentation.characters.list.CharacterListFragment
import com.malombardi.marvel.presentation.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharactersBinding

    private lateinit var viewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CharactersViewModel::class.java]
        binding = ActivityCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        lifecycleScope.collectFlow(viewModel.uiState) { state ->
            when (state) {
                is CharactersActivityUiState.ListState -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, CharacterListFragment()).commit()
                }
                is CharactersActivityUiState.DetailState -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, CharacterDetailFragment(state.character))
                        .commit()
                }
            }
        }

    }

}