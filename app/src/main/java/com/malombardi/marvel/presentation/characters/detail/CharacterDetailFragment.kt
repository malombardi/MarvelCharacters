package com.malombardi.marvel.presentation.characters.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.malombardi.marvel.R
import com.malombardi.data.Constants
import com.malombardi.marvel.databinding.FragmentCharacterDetailBinding
import com.malombardi.domain.models.MarvelCharacter
import com.malombardi.marvel.presentation.characters.CharactersViewModel
import com.malombardi.marvel.presentation.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailBinding

    val viewModel: CharacterDetailViewModel by viewModels()
    private val sharedViewModel: CharactersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setSelectedCharacter(arguments?.get(Constants.CHARACTER_ARGS) as MarvelCharacter?)
        with(binding) {
            lifecycleScope.collectFlow(viewModel.uiState) { state ->
                when (state) {
                    is CharacterDetailUiState.Loading -> {
                        progress.visibility = View.VISIBLE
                        errorLayout.errorLayout.visibility = View.GONE
                    }
                    is CharacterDetailUiState.Error -> {
                        progress.visibility = View.GONE
                        errorLayout.errorLayout.visibility = View.VISIBLE
                    }
                    is CharacterDetailUiState.Success -> {
                        progress.visibility = View.GONE
                        errorLayout.errorLayout.visibility = View.GONE
                        characterDetailName.text = state.character.name
                        characterDetailDescription.text = state.character.description
                        val characterBigImage = state.character.thumbnail?.replace(
                            Constants.IMAGE_DEFAULT_SIZE,
                            Constants.IMAGE_BIG_SIZE
                        )

                        Glide
                            .with(requireContext())
                            .load(characterBigImage)
                            .placeholder(R.drawable.not_available)
                            .error(R.drawable.not_available)
                            .fitCenter()
                            .into(characterDetailPic)

                        characterDetailComics.visibility =
                            if (state.character.comicsCount!! > com.malombardi.domain.Constants.COMICS_EMPTY) {
                                View.VISIBLE
                            } else {
                                View.GONE
                            }

                        characterDetailBio.visibility =
                            if (!state.character.url.isNullOrBlank()) {
                                View.VISIBLE
                            } else {
                                View.GONE
                            }

                        characterDetailBio.setOnClickListener {
                            sharedViewModel.onBioSelected(state.character.url)
                        }

                        characterDetailComics.setOnClickListener {
                            sharedViewModel.onComicsSelected(state.character.id)
                        }
                    }
                }
            }
        }
    }
}
