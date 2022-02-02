package com.malombardi.marvel.domain.fake_datasources

import com.malombardi.marvel.data.db.relations.CharacterWithComics
import com.malombardi.marvel.domain.models.*

class FakeDataSource {
    companion object {
        const val MAX_RESULTS = 5

        fun getFakeMarvelCharacter(): List<MarvelCharacter> {
            val fakeCharacterList = ArrayList<MarvelCharacter>()

            for(i in 0 until MAX_RESULTS) {
                fakeCharacterList.add(MarvelCharacter("",
                    i,
                    "",
                    "",
                    ""))
            }

            return fakeCharacterList
        }

        fun getFakeMarvelComic(): List<MarvelComic> {
            val fakeComicList = ArrayList<MarvelComic>()

            for(i in 0 until MAX_RESULTS) {
                fakeComicList.add(MarvelComic(null,
                    null,
                    null,
                    i,
                    null,
                    null))
            }
            return fakeComicList
        }
    }
}
