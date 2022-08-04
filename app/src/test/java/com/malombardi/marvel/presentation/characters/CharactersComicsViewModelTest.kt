package com.malombardi.marvel.presentation.characters

import app.cash.turbine.test
import com.malombardi.domain.ResponseWrapper
import com.malombardi.domain.models.MarvelComic
import com.malombardi.domain.usecases.GetComicsUseCase
import com.malombardi.marvel.presentation.characters.comics.CharacterComicsUiState
import com.malombardi.marvel.presentation.characters.comics.CharactersComicsViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersComicsViewModelTest {

    private lateinit var charactersComicsViewModel: CharactersComicsViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var getComicsUseCase : GetComicsUseCase

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)

        every {
            getComicsUseCase.invoke(any())
        } returns (flow {
            emit(ResponseWrapper.Success(getFakeMarvelComic()))
        })

        charactersComicsViewModel = CharactersComicsViewModel(getComicsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on viewmodel init uiState is Loading`() = runBlocking {
        //Given

        //When

        //Then
        charactersComicsViewModel.uiState.test {
            Assert.assertEquals(
                CharacterComicsUiState.Loading,
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on viewmodel startFetching with valid characterId uiState is Success`() = runBlocking {
        //Given
        val characterId = "TEST"
        //When
        charactersComicsViewModel.startFetching(characterId)
        //Then
        charactersComicsViewModel.uiState.test {
            Assert.assertEquals(
                CharacterComicsUiState.Success(getFakeMarvelComic()),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun getFakeMarvelComic(): List<MarvelComic> {
        val fakeComicList = ArrayList<MarvelComic>()

        for (i in 0 until 5) {
            fakeComicList.add(
                MarvelComic(
                    null,
                    null,
                    null,
                    i,
                    null,
                    null
                )
            )
        }
        return fakeComicList
    }
}
