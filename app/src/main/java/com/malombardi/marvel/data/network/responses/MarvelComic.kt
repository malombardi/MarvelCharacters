package com.malombardi.marvel.data.network.responses

data class MarvelComic(
    val creators: Creators?,
    val description: String?,
    val digitalId: Int?,
    val id: Int?,
    val thumbnail: Thumbnail?,
    val title: String?
)
