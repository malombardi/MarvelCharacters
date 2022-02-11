package com.malombardi.marvel.presentation.comics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.malombardi.marvel.databinding.ViewholderCharacterBinding
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic
import com.malombardi.marvel.presentation.characters.CharactersViewModel
import com.malombardi.marvel.presentation.collectFlow
import com.malombardi.marvel.presentation.onClickEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ComicsAdapter :
    ListAdapter<MarvelComic, ComicViewHolder>(ComicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val itemBinding =
            ViewholderCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicViewHolder(parent.context, itemBinding)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        setData(item)
    }

    class ComicDiffCallback : DiffUtil.ItemCallback<MarvelComic>() {
        override fun areItemsTheSame(oldItem: MarvelComic, newItem: MarvelComic): Boolean {
            return oldItem.digitalId == newItem.digitalId
        }

        override fun areContentsTheSame(
            oldItem: MarvelComic,
            newItem: MarvelComic
        ): Boolean {
            return oldItem == newItem
        }
    }
}
