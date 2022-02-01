package com.malombardi.marvel.presentation.characters.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.malombardi.marvel.databinding.ViewholderCharacterBinding
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.presentation.collectFlow
import com.malombardi.marvel.presentation.onClickEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CharacterAdapter(
    private val viewModel: CharacterListViewModel,
    private val scope: CoroutineScope
) :
    ListAdapter<MarvelCharacter, CharacterViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemBinding =
            ViewholderCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(parent.context, itemBinding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        setData(item)
        scope.collectFlow(itemView.onClickEvents) {
            viewModel.onItemSelected(item)
        }
    }

    class CharacterDiffCallback : DiffUtil.ItemCallback<MarvelCharacter>() {
        override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MarvelCharacter,
            newItem: MarvelCharacter
        ): Boolean {
            return oldItem == newItem
        }
    }
}
