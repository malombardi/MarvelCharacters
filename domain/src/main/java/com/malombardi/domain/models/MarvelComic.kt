package com.malombardi.domain.models

import com.malombardi.domain.Constants

data class MarvelComic(
    val creators: List<MarvelCommicCreator>? = null,
    val description: String? = "",
    val digitalId: Int? = Constants.UNKNOWN_ID,
    val id: Int? = Constants.UNKNOWN_ID,
    val thumbnail: String? = "",
    val title: String? = ""
)
