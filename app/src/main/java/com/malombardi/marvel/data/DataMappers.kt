package com.malombardi.marvel.data

import com.malombardi.marvel.data.Constants.IMAGE_DEFAULT_SIZE
import com.malombardi.marvel.data.Constants.URL_BIO_TYPE
import com.malombardi.marvel.domain.Constants
import com.malombardi.marvel.domain.models.MarvelCommicCreator
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic
import com.malombardi.marvel.data.db.entities.MarvelCharacterEntity as LocalCharacter
import com.malombardi.marvel.data.db.entities.MarvelComicEntity as LocalComic
import com.malombardi.marvel.data.db.entities.MarvelCreatorEntity as LocalCreator
import com.malombardi.marvel.data.network.responses.MarvelCharacterResponse as RemoteCharacter
import com.malombardi.marvel.data.network.responses.MarvelComicResponse as RemoteComic

fun RemoteCharacter.toDomainCharacterList(): List<MarvelCharacter> {
    return data.results.map {
        val img = if (it.thumbnail != null) {
            it.thumbnail.path + "/" + IMAGE_DEFAULT_SIZE + "." + it.thumbnail.extension
        } else {
            ""
        }
        val url = if (!it.urls.isNullOrEmpty()) {
            var tempUrl = ""
            for (url in it.urls) {
                if (url.type == URL_BIO_TYPE) {
                    tempUrl = url.url!!
                    break
                }
            }
            tempUrl
        } else {
            ""
        }
        MarvelCharacter(
            description = it.description,
            id = it.id,
            name = it.name,
            thumbnail = img,
            url = url,
            comicsCount = it.comics?.available ?: Constants.COMICS_EMPTY
        )
    }
}

fun RemoteComic.toDomainComicList(): List<MarvelComic> {
    return data.results.map {
        val img = if (it.thumbnail != null) {
            it.thumbnail.path + "/" + IMAGE_DEFAULT_SIZE + "." + it.thumbnail.extension
        } else {
            ""
        }
        val creators = if (it.creators != null && !it.creators.items.isNullOrEmpty()) {
            val creatorsList = ArrayList<MarvelCommicCreator>()
            for (creatorItem in it.creators.items) {
                creatorsList.add(
                    MarvelCommicCreator(
                        resourceURI = creatorItem.resourceURI,
                        name = creatorItem.name,
                        role = creatorItem.role
                    )
                )
            }
            creatorsList
        } else {
            listOf<MarvelCommicCreator>()
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
        url = this.bioLink,
        comicsCount = this.comicsCount
    )
}

fun LocalComic.toDomainComic(): MarvelComic {
    val creators = if (this.creators != null && !this.creators.isNullOrEmpty()) {
        val creatorsList = ArrayList<MarvelCommicCreator>()
        for (creatorItem in this.creators) {
            creatorsList.add(
                MarvelCommicCreator(
                    resourceURI = creatorItem.resourceURI,
                    name = creatorItem.name,
                    role = creatorItem.role
                )
            )
        }
        creatorsList
    } else {
        listOf<MarvelCommicCreator>()
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

fun LocalCreator.toDomainCreator(): MarvelCommicCreator {
    return MarvelCommicCreator(
        resourceURI = this.resourceURI,
        name = this.name,
        role = this.role
    )
}

fun MarvelCommicCreator.toLocalCreator(): LocalCreator {
    return LocalCreator(
        resourceURI = this.resourceURI!!,
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
        bioLink = this.url,
        comicsCount = this.comicsCount
    )
}

fun List<MarvelCharacter>.toLocalCharacterList(): List<LocalCharacter> {
    return this.map {
        LocalCharacter(
            id = it.id,
            name = it.name,
            description = it.description,
            thumbnail = it.thumbnail,
            bioLink = it.url,
            comicsCount = it.comicsCount
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
