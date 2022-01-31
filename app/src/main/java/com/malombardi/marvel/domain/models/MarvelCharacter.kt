package com.malombardi.marvel.domain.models

import com.malombardi.marvel.domain.Constants.UNKNOWN_ID

data class MarvelCharacter(
    val description: String? = "",
    val id: Int? = UNKNOWN_ID,
    val name: String? = "",
    val thumbnail: Thumbnail? = null,
    val urls: List<Url>? = null
)