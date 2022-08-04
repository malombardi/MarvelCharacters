package com.malombardi.data

import com.malombardi.data.db.dao.MarvelDao
import com.malombardi.data.db.relations.CharacterComicCrossRef
import com.malombardi.data.db.relations.ComicCreatorCrossRef
import com.malombardi.domain.datasources.LocalDataSource
import com.malombardi.domain.models.MarvelCharacter
import com.malombardi.domain.models.MarvelComic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val dao: MarvelDao) : LocalDataSource {

    override suspend fun charactersSize(): Int {
        return dao.charactersCount()
    }

    override suspend fun searchSize(startWith: String): Int {
        return dao.searchCount(startWith)
    }

    override suspend fun comicsSize(characterId: String): Int {
        return dao.comicsCount(characterId)
    }

    override suspend fun saveCharacters(characters: List<MarvelCharacter>) {
        dao.saveCharacter(characters.toLocalCharacterList())
    }

    override suspend fun saveSearch(characters: List<MarvelCharacter>) {
        dao.saveCharacter(characters.toLocalCharacterList())
    }

    override suspend fun saveComics(characterId: String, comics: List<MarvelComic>) {
        dao.saveComic(comics.toLocalComicList())
        for (comicToSave in comics) {
            val characterComicJoin = CharacterComicCrossRef(characterId.toInt(),
                comicToSave.id ?: return
            )
            dao.saveCharacterComic(characterComicJoin)
            comicToSave.creators?.let {
                for ((resourceURI) in it) {
                    val comicCreatorJoin =
                        ComicCreatorCrossRef(comicToSave.id ?: return@let,
                            resourceURI ?: return@let
                        )
                    dao.saveComicCreator(comicCreatorJoin)
                }
            }
        }
    }

    override fun getCharacters(): Flow<List<MarvelCharacter>> =
        dao.getCharacters().map { localCharacter ->
            localCharacter.map { it.toDomainCharacter() }
        }

    override fun searchCharacters(startWith: String): Flow<List<MarvelCharacter>> =
        dao.getCharactersWithName(startWith).map { localCharacter ->
            localCharacter.map { it.toDomainCharacter() }
        }

    override fun getComics(characterId: String): Flow<List<MarvelComic>> = flow {
        val comicsList = dao.getComicsForCharacter(characterId)[0].comics!!.toDomainComicList()
        emit(comicsList)
    }
}
