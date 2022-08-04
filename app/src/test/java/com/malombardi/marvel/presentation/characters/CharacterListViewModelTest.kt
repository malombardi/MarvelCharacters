package com.malombardi.marvel.presentation.characters

import app.cash.turbine.test
import com.malombardi.domain.ResponseWrapper
import com.malombardi.domain.models.MarvelCharacter
import com.malombardi.domain.usecases.GetCharactersUseCase
import com.malombardi.domain.usecases.SearchCharactersUseCase
import com.malombardi.marvel.presentation.characters.list.CharacterListUiState
import com.malombardi.marvel.presentation.characters.list.CharacterListViewModel
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterListViewModelTest {

    private lateinit var characterListViewModel: CharacterListViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var getCharactersUserCase: GetCharactersUseCase

    @MockK
    private lateinit var searchCharactersUseCase: SearchCharactersUseCase

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)

        every {
            getCharactersUserCase.invoke(any())
        } returns (flow {
            emit(ResponseWrapper.Success(getFakeMarvelCharacter()))
        })

        every {
            searchCharactersUseCase.invoke(any())
        } returns (flow {
            emit(ResponseWrapper.Success(getFakeMarvelCharacter().reversed()))
        })

        characterListViewModel =
            CharacterListViewModel(getCharactersUserCase, searchCharactersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on viewmodel init uiState is Success`() = runBlocking {
        //Given

        //When

        //Then
        characterListViewModel.uiState.test {
            assertEquals(CharacterListUiState.SuccessState(getFakeMarvelCharacter()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on viewmodel when search uiState is Success`() = runBlocking {
        //Given
        val searchText = "TEST"
        //When
        characterListViewModel.searchText.value = searchText
        //Then
        characterListViewModel.uiState.test {
            assertEquals(CharacterListUiState.SuccessState(getFakeMarvelCharacter().reversed()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun getFakeMarvelCharacter(): List<MarvelCharacter> {
        val fakeCharacterList = ArrayList<MarvelCharacter>()

        for (i in 0 until 5) {
            fakeCharacterList.add(
                MarvelCharacter(
                    "",
                    i,
                    "",
                    "",
                    ""
                )
            )
        }

        return fakeCharacterList
    }
}
