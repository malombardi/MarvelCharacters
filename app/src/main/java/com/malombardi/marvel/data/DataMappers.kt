package com.malombardi.marvel.data

import com.malombardi.marvel.data.Constants.IMAGE_DEFAULT_SIZE
import com.malombardi.marvel.data.Constants.URL_BIO_TYPE
import com.malombardi.marvel.domain.models.Creator
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic
import com.malombardi.marvel.data.db.entities.MarvelCharacterEntity as LocalCharacter
import com.malombardi.marvel.data.db.entities.MarvelComicEntity as LocalComic
import com.malombardi.marvel.data.db.entities.MarvelCreatorEntity as LocalCreator
import com.malombardi.marvel.domain.repository.network.responses.MarvelCharacterResponse as RemoteCharacter
import com.malombardi.marvel.domain.repository.network.responses.MarvelComicResponse as RemoteComic

fun RemoteCharacter.toDomainCharacterList(): List<MarvelCharacter> {
    return data.results.map {
        val img = if (it.thumbnail != null) {
            it.thumbnail.path + "/" + IMAGE_DEFAULT_SIZE + it.thumbnail.extension
        } else {
            ""
        }
        val url = if (!it.urls.isNullOrEmpty()) {
            for (url in it.urls) {
                if (url.type == URL_BIO_TYPE) {
                    url.url
                }
            }
            ""
        } else {
            ""
        }
        MarvelCharacter(
            description = it.description,
            id = it.id,
            name = it.name,
            thumbnail = img,
            url = url
        )
    }
}

fun RemoteComic.toDomainComicList(): List<MarvelComic> {
    return data.results.map {
        val img = if (it.thumbnail != null) {
            it.thumbnail.path + "/" + IMAGE_DEFAULT_SIZE + it.thumbnail.extension
        } else {
            ""
        }
        val creators = if (it.creators != null && !it.creators.items.isNullOrEmpty()) {
            val creatorsList = ArrayList<Creator>()
            for (creatorItem in it.creators.items) {
                creatorsList.add(
                    Creator(
                        resourceURI = creatorItem.resourceURI,
                        name = creatorItem.name,
                        role = creatorItem.role
                    )
                )
            }
            creatorsList
        } else {
            listOf<Creator>()
        }
        MarvelComic(
            creators = creators,
            description = it.description,
            digitalId = it.digitalId,
            id = it.id,
            thumbnail = img,
            title = it.title
        )
    }
}

fun LocalCharacter.toDomainCharacter(): MarvelCharacter {
    return MarvelCharacter(
        description = this.description,
        id = this.id,
        name = this.name,
        thumbnail = this.thumbnail,
        url = this.bioLink
    )
}

fun LocalComic.toDomainComic(): MarvelComic {
    val creators = if (this.creators != null && !this.creators.isNullOrEmpty()) {
        val creatorsList = ArrayList<Creator>()
        for (creatorItem in this.creators) {
            creatorsList.add(
                Creator(
                    resourceURI = creatorItem.resourceURI,
                    name = creatorItem.name,
                    role = creatorItem.role
                )
            )
        }
        creatorsList
    } else {
        listOf<Creator>()
    }
    return MarvelComic(
        creators = creators,
        description = this.description,
        digitalId = this.digitalId,
        id = this.id,
        thumbnail = this.thumbnail,
        title = this.title
    )
}

fun LocalCreator.toDomainCreator(): Creator {
    return Creator(
        resourceURI = this.resourceURI,
        name = this.name,
        role = this.role
    )
}

fun Creator.toLocalCreator(): LocalCreator {
    return LocalCreator(
        resourceURI = this.resourceURI,
        name = this.name,
        role = this.role
    )
}

fun MarvelCharacter.toLocalCharacter(): LocalCharacter {
    return LocalCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnail = this.thumbnail,
        bioLink = this.url
    )
}

fun List<MarvelCharacter>.toLocalCharacterList(): List<LocalCharacter> {
    return this.map {
        LocalCharacter(
            id = it.id,
            name = it.name,
            description = it.description,
            thumbnail = it.thumbnail,
            bioLink = it.url
        )
    }
}

fun List<MarvelComic>.toLocalComicList(): List<LocalComic> {
    return this.map {
        LocalComic(
            id = it.id,
            digitalId = it.digitalId,
            description = it.description,
            thumbnail = it.thumbnail,
            creators = it.creators?.map { creator -> creator.toLocalCreator() },
            title = it.title
        )
    }
}

fun List<LocalComic>.toDomainComicList(): List<MarvelComic> {
    return this.map {
        MarvelComic(
            creators = it.creators?.map { creator -> creator.toDomainCreator() },
            description = it.description,
            digitalId = it.digitalId,
            id = it.id,
            thumbnail = it.thumbnail,
            title = it.title
        )
    }
}