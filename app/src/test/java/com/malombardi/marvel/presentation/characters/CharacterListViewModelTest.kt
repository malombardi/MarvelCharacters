package com.malombardi.marvel.presentation.characters

import app.cash.turbine.test
import com.malombardi.marvel.domain.errors.ErrorHandler
import com.malombardi.marvel.domain.fake_datasources.FakeDataSource
import com.malombardi.marvel.domain.fake_datasources.FakeDataSource.Companion.getFakeMarvelCharacter
import com.malombardi.marvel.domain.fake_datasources.FakeLocalDataSource
import com.malombardi.marvel.domain.fake_datasources.FakeRemoteDataSource
import com.malombardi.marvel.domain.repository.Repository
import com.malombardi.marvel.domain.usecases.GetCharactersUseCase
import com.malombardi.marvel.domain.usecases.SearchCharactersUseCase
import com.malombardi.marvel.presentation.characters.list.CharacterListUiState
import com.malombardi.marvel.presentation.characters.list.CharacterListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterListViewModelTest {

    private lateinit var characterListViewModel: CharacterListViewModel

    private val dispatcher = TestCoroutineDispatcher()
    private val localDataSource = FakeLocalDataSource()
    private val remoteDataSource = FakeRemoteDataSource()
    private val errorHandler = ErrorHandler()

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()
    private val repository = Repository(localDataSource, remoteDataSource)

    @ExperimentalCoroutinesApi
    private val getCharactersUserCase = GetCharactersUseCase(repository, coroutineDispatcher, errorHandler)
    private val searchCharactersUseCase = SearchCharactersUseCase(repository, coroutineDispatcher, errorHandler)

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        characterListViewModel = CharacterListViewModel(getCharactersUserCase, searchCharactersUseCase)
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
            assert(awaitItem() == CharacterListUiState.LoadingState(true))
            assert(awaitItem() == CharacterListUiState.SuccessState(getFakeMarvelCharacter()))
            cancel()
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
            assert(awaitItem() == CharacterListUiState.SuccessState(getFakeMarvelCharacter().reversed()))
            cancel()
        }
    }
}
