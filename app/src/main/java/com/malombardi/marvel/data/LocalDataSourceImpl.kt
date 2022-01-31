package com.malombardi.marvel.data

import com.malombardi.marvel.data.db.MarvelCharacterDataBase
import com.malombardi.marvel.data.db.relations.CharacterComicCrossRef
import com.malombardi.marvel.data.db.relations.ComicCreatorCrossRef
import com.malombardi.marvel.domain.datasources.LocalDataSource
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.models.MarvelComic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(db: MarvelCharacterDataBase) : LocalDataSource {

    private val dao = db.marvelDao()

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
            val characterComicJoin = CharacterComicCrossRef(characterId.toInt(), comicToSave.id!!)
            dao.saveCharacterComic(characterComicJoin)
            comicToSave.creators?.let {
                for (creator in it) {
                    val comicCreatorJoin =
                        ComicCreatorCrossRef(comicToSave.id, creator.resourceURI!!)
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

    override fun getComics(characterId: String): Flow<List<MarvelComic>> {
        val comicsList = dao.getComicsForCharacter(characterId)[0].comics?.toDomainComicList()
        return if (comicsList.isNullOrEmpty()){
            flowOf(listOf())
        }else {
            flowOf(comicsList)
        }
    }
}
