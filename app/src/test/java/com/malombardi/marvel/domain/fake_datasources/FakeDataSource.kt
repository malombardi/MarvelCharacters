package com.malombardi.marvel.domain.fake_datasources

import com.malombardi.marvel.domain.models.*

class FakeDataSource {
    companion object {
        const val MAX_RESULTS = 5

        fun getFakeCharacterData() : CharacterData {
            val fakeCharactersList = ArrayList<MarvelCharacter>()

            for(i in 0 until MAX_RESULTS) {
                fakeCharactersList.add(getFakeMarvelCharacter(i))
            }
            return CharacterData(fakeCharactersList)
        }

        private fun getFakeMarvelCharacter(id: Int): MarvelCharacter {
            return MarvelCharacter("",
                id,
                "",
                Thumbnail("",""),
                listOf<Url>())
        }

        fun getFakeComicData() : ComicData {
            val fakeComicList = ArrayList<MarvelComic>()

            for(i in 0 until MAX_RESULTS) {
                fakeComicList.add(getFakeMarvelComic(i))
            }
            return ComicData(fakeComicList)
        }

        private fun getFakeMarvelComic(id: Int): MarvelComic {
            return MarvelComic(null,
                null,
                null,
                id,
                null,
                null)
        }
    }
}
