package com.malombardi.marvel.domain.models

data class MarvelComicResponse(
    val data: ComicData,
)

data class ComicData(
    val results: List<MarvelComic>
)

data class Creators(
    val items: List<Creator>?
)

data class Creator(
    val name: String?,
    val resourceURI: String?,
    val role: String?
)
