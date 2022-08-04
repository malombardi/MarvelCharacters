package com.malombardi.domain.models

import com.malombardi.domain.Constants
import java.io.Serializable

data class MarvelCharacter(
    val description: String? = "",
    val id: Int? = Constants.UNKNOWN_ID,
    val name: String? = "",
    val thumbnail: String? = null,
    val url: String? = null,
    val comicsCount: Int? = Constants.COMICS_EMPTY
): Serializable
