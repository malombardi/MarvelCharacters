package com.malombardi.marvel.presentation.characters

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.malombardi.marvel.R
import com.malombardi.data.Constants
import com.malombardi.marvel.databinding.ActivityCharactersBinding
import com.malombardi.marvel.presentation.characters.bio.CharacterBioDialog
import com.malombardi.marvel.presentation.characters.detail.CharacterDetailFragment
import com.malombardi.marvel.presentation.characters.list.CharacterListFragment
import com.malombardi.marvel.presentation.collectFlow
import com.malombardi.marvel.presentation.characters.comics.CharacterComicsDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharactersBinding

    private lateinit var viewModel: CharactersViewModel

    private val listFragment = CharacterListFragment()

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
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, listFragment)
                        .commit()
                }
                is CharactersActivityUiState.DetailState -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, CharacterDetailFragment().apply {
                            val args = Bundle().apply {
                                putSerializable(Constants.CHARACTER_ARGS, state.character)
                            }
                            arguments = args
                        })
                        .commit()
                }
                is CharactersActivityUiState.ErrorState -> {
                    val defaultErrorString = resources.getString(R.string.default_internal_error)
                    Toast
                        .makeText(this, defaultErrorString, Toast.LENGTH_LONG)
                        .show()
                }
                is CharactersActivityUiState.BioState -> {
                    CharacterBioDialog.newInstance(state.link, supportFragmentManager).showsDialog
                }
                is CharactersActivityUiState.ComicsState -> {
                    CharacterComicsDialog.newInstance(
                        state.characterId.toString(),
                        supportFragmentManager
                    ).showsDialog
                }
            }
        }
    }

    override fun onBackPressed() {
        if (listFragment.isVisible) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
            viewModel.onDetailReturn()
        }
    }
}
