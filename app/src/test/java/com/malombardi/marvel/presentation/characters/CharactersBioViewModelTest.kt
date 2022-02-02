package com.malombardi.marvel.presentation.characters

import app.cash.turbine.test
import com.malombardi.marvel.presentation.characters.bio.CharacterBioUiState
import com.malombardi.marvel.presentation.characters.bio.CharactersBioViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CharactersBioViewModelTest {

    private lateinit var charactersBioViewModel: CharactersBioViewModel

    @Before
    fun setup() {
        charactersBioViewModel = CharactersBioViewModel()
    }

    @Test
    fun `on viewmodel init screenstate is InitialLoading`() = runBlocking {
        //Given

        //When

        //Then
        charactersBioViewModel.uiState.test {
            assert(awaitItem() == CharacterBioUiState.InitialLoading)
            cancel()
        }
    }

    @Test
    fun `on setUrl with empty string uiState change to Error`() = runBlocking {
        //Given
        val url = ""

        //When
        charactersBioViewModel.setUrl(url)

        //Then
        charactersBioViewModel.uiState.test {
            assert(awaitItem() == CharacterBioUiState.Error)
            cancel()
        }
    }

    @Test
    fun `on setUrl with non empty string uiState change to Loading`() = runBlocking {
        //Given
        val url = "Some URL"

        //When
        charactersBioViewModel.setUrl(url)

        //Then
        charactersBioViewModel.uiState.test {
            assert(awaitItem() == CharacterBioUiState.Loading(url))
            cancel()
        }
    }

    @Test
    fun `onPageLoaded uiState change to DataLoaded`() = runBlocking {
        //Given

        //When
        charactersBioViewModel.onPageLoaded()

        //Then
        charactersBioViewModel.uiState.test {
            assert(awaitItem() == CharacterBioUiState.DataLoaded)
            cancel()
        }
    }
}
