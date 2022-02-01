package com.malombardi.marvel.presentation.characters.list

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.malombardi.marvel.R
import com.malombardi.marvel.databinding.ViewholderCharacterBinding
import com.malombardi.marvel.domain.models.MarvelCharacter

class CharacterViewHolder(
    private val context: Context,
    private val itemBinding: ViewholderCharacterBinding
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun setData(item: MarvelCharacter) {
        with(itemBinding) {
            Glide
                .with(context)
                .load(item.thumbnail)
                .centerCrop()
                .into(characterImage)
                .onLoadFailed(context.getDrawable(R.drawable.ic_logo))

            characterName.text = item.name
        }
    }
}
