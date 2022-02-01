package com.malombardi.marvel.presentation.characters.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.malombardi.marvel.R
import com.malombardi.marvel.data.Constants
import com.malombardi.marvel.databinding.FragmentCharacterDetailBinding
import com.malombardi.marvel.databinding.FragmentCharacterListBinding
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.presentation.characters.list.CharacterAdapter
import com.malombardi.marvel.presentation.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lifecycleScope.collectFlow(viewModel.character) { marvelCharacter ->
                characterDetailName.text = marvelCharacter.name
                characterDetailDescription.text = marvelCharacter.description
                val characterBigImage = marvelCharacter.thumbnail?.replace(Constants.IMAGE_DEFAULT_SIZE,Constants.IMAGE_BIG_SIZE)

                Glide
                    .with(requireContext())
                    .load(characterBigImage)
                    .placeholder(R.drawable.not_available)
                    .error(R.drawable.not_available)
                    .fitCenter()
                    .into(characterDetailPic)

                characterDetailComics.visibility =
                    if(marvelCharacter.comicsCount!! > com.malombardi.marvel.domain.Constants.COMICS_EMPTY) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                characterDetailBio.visibility =
                    if(marvelCharacter.url !=null) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }
        }
    }

    companion object {
        const val NAME = "CharacterDetailFragment"
    }
}