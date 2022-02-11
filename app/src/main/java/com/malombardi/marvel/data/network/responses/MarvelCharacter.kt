package com.malombardi.marvel.data.network.responses

import com.malombardi.marvel.domain.Constants.COMICS_EMPTY
import com.malombardi.marvel.domain.Constants.UNKNOWN_ID

data class MarvelCharacter(
    val description: String? = "",
    val id: Int? = UNKNOWN_ID,
    val name: String? = "",
    val thumbnail: Thumbnail? = null,
    val urls: List<Url>? = null,
    val comics: Comic? = null,
)

data class Comic(
    val available : Int? =COMICS_EMPTY,
)
