package com.malombardi.marvel.domain.models

import com.malombardi.marvel.domain.Constants

data class MarvelComic(
    val creators: List<Creator>? = null,
    val description: String? = "",
    val digitalId: Int? = Constants.UNKNOWN_ID,
    val id: Int? = Constants.UNKNOWN_ID,
    val thumbnail: String? = "",
    val title: String? = ""
)
