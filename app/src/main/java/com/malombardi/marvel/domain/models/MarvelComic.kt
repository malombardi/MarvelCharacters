package com.malombardi.marvel.domain.models

data class MarvelComic(
    val creators: List<Creator>?,
    val description: String?,
    val digitalId: Int?,
    val id: Int?,
    val thumbnail: String?,
    val title: String?
)