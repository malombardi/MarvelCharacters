package com.malombardi.marvel.presentation.characters

import app.cash.turbine.test
import com.malombardi.marvel.domain.errors.ErrorEntity
import com.malombardi.marvel.domain.errors.ErrorHandler
import com.malombardi.marvel.domain.fake_datasources.FakeDataSource.Companion.getFakeMarvelComic
import com.malombardi.marvel.domain.fake_datasources.FakeLocalDataSource
import com.malombardi.marvel.domain.fake_datasources.FakeRemoteDataSource
import com.malombardi.marvel.domain.repository.Repository
import com.malombardi.marvel.domain.usecases.GetComicsUseCase
import com.malombardi.marvel.presentation.characters.comics.CharacterComicsUiState
import com.malombardi.marvel.presentation.characters.comics.CharactersComicsViewModel
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
class CharactersComicsViewModelTest {

    private lateinit var charactersComicsViewModel: CharactersComicsViewModel

    private val dispatcher = TestCoroutineDispatcher()
    private val localDataSource = FakeLocalDataSource()
    private val remoteDataSource = FakeRemoteDataSource()
    private val errorHandler = ErrorHandler()

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()
    private val repository = Repository(localDataSource, remoteDataSource)

    @ExperimentalCoroutinesApi
    private val getComicsUseCase =
        GetComicsUseCase(repository, coroutineDispatcher, errorHandler)

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
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
            assert(awaitItem() == CharacterComicsUiState.Loading)
            cancel()
        }
    }

    @Test
    fun `on viewmodel startFetching with empty characterId uiState is Error`() = runBlocking {
        //Given
        val characterId = ""
        //When
        charactersComicsViewModel.startFetching(characterId)
        //Then
        charactersComicsViewModel.uiState.test {
            assert(awaitItem() == CharacterComicsUiState.Error(
                ErrorEntity.InternalError(
                    ErrorHandler.EMPTY_DATA_ERROR_CODE
                )))
            cancel()
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
            assert(awaitItem() == CharacterComicsUiState.Loading)
            assert(awaitItem() == CharacterComicsUiState.Success(getFakeMarvelComic()))
            cancel()
        }
    }

}
