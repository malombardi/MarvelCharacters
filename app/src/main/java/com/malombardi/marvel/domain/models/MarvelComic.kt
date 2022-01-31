package com.malombardi.marvel.domain.models

data class MarvelComic(
    val creators: Creators?,
    val description: String?,
    val digitalId: Int?,
    val id: Int?,
    val thumbnail: Thumbnail?,
    val title: String?
)
