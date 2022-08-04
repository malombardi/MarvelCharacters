package com.malombardi.marvel.presentation.characters

import app.cash.turbine.test
import com.malombardi.data.ErrorHandler
import com.malombardi.domain.errors.ErrorEntity
import com.malombardi.domain.models.MarvelCharacter
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CharactersViewModelTest {

    private lateinit var charactersViewModel: CharactersViewModel

    @Before
    fun setup() {
        charactersViewModel = CharactersViewModel()
    }

    @Test
    fun `on viewmodel init screenstate is ListState`() = runBlocking {
        //Given

        //When

        //Then
        charactersViewModel.uiState.test {
            assert(awaitItem() == CharactersActivityUiState.ListState)
            cancel()
        }
    }

    @Test
    fun `onItemSelected uiState change to DetailsState`() = runBlocking {
        //Given
        val marvelCharacter = mockEmptyCharacter()

        //When
        charactersViewModel.onItemSelected(marvelCharacter)

        //Then
        charactersViewModel.uiState.test {
            assert(awaitItem() == CharactersActivityUiState.DetailState(marvelCharacter))
            cancel()
        }
    }

    @Test
    fun `onBioSelected with empty url uiState change to ErrorState`() = runBlocking {
        //Given
        val marvelCharacter = mockEmptyCharacter()

        //When
        charactersViewModel.onBioSelected(marvelCharacter.url)

        //Then
        charactersViewModel.uiState.test {
            assert(awaitItem() == CharactersActivityUiState.ErrorState(
                ErrorEntity.InternalError(
                    ErrorHandler.UNKNOWN_ERROR_CODE
                )))
            cancel()
        }
    }

    @Test
    fun `onBioSelected with NOT empty url uiState change to BioState`() = runBlocking {
        //Given
        val marvelCharacter = mockFakeCharacter()

        //When
        charactersViewModel.onBioSelected(marvelCharacter.url)

        //Then
        charactersViewModel.uiState.test {
            assert(awaitItem() == CharactersActivityUiState.BioState(marvelCharacter.url!!))
            cancel()
        }
    }

    @Test
    fun `test full flow with character`() = runBlocking {
        //Given
        val marvelCharacter = mockFakeCharacter()

        //When

        //Then
        charactersViewModel.uiState.test {
            assert(awaitItem() == CharactersActivityUiState.ListState)
            charactersViewModel.onItemSelected(marvelCharacter)
            assert(awaitItem() == CharactersActivityUiState.DetailState(marvelCharacter))
            charactersViewModel.onBioSelected(marvelCharacter.url!!)
            assert(awaitItem() == CharactersActivityUiState.BioState(marvelCharacter.url!!))
            charactersViewModel.onActivityReturned()
            assert(awaitItem() == CharactersActivityUiState.DetailState(marvelCharacter))
            charactersViewModel.onComicsSelected(marvelCharacter.id)
            assert(awaitItem() == CharactersActivityUiState.ComicsState(marvelCharacter.id!!))
            charactersViewModel.onActivityReturned()
            assert(awaitItem() == CharactersActivityUiState.DetailState(marvelCharacter))
            charactersViewModel.onDetailReturn()
            assert(awaitItem() == CharactersActivityUiState.ListState)
            cancel()
        }
    }

    private fun mockEmptyCharacter(): MarvelCharacter{
        return MarvelCharacter()
    }

    private fun mockFakeCharacter(): MarvelCharacter{
        return MarvelCharacter(
            description = "Some description",
            id = 1234,
            name = "Some name",
            thumbnail = "Some thumbnail",
            url = "Some url",
            comicsCount = 1234
        )
    }
}
