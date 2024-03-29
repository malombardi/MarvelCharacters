package com.malombardi.marvel.presentation.comics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.malombardi.domain.models.MarvelComic
import com.malombardi.marvel.databinding.ViewholderCharacterBinding

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
