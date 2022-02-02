package com.malombardi.marvel.presentation.characters.list

import androidx.lifecycle.viewModelScope
import com.malombardi.marvel.domain.Constants
import com.malombardi.marvel.domain.Constants.MIN_SEARCH_TEXT_SIZE
import com.malombardi.marvel.domain.ResponseWrapper
import com.malombardi.marvel.domain.usecases.GetCharactersUseCase
import com.malombardi.marvel.domain.usecases.SearchCharactersUseCase
import com.malombardi.marvel.presentation.MarvelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val searchCharactersUseCase: SearchCharactersUseCase
) :
    MarvelViewModel() {

    val lastItemVisible = MutableStateFlow(0)
    val searchText = MutableStateFlow("")
    private val isSearching = MutableStateFlow(false)

    val uiState = MutableStateFlow<CharacterListUiState>(CharacterListUiState.LoadingState(true))

    init {
        viewModelScope.launch {
            searchText.collect {
                //start search when the user input at least 3 chars
                isSearching.value = (!it.isNullOrBlank() && it.length >= MIN_SEARCH_TEXT_SIZE)
                notifyLastVisible(lastItemVisible.value)
            }
        }

        viewModelScope.launch {
            lastItemVisible.collect {
                notifyLastVisible(it)
            }
        }
    }

    private fun notifyLastVisible(lastVisible: Int) {
        if (!isSearching.value) {
            getCharacters(lastVisible)
        } else {
            searchCharacter(searchText.value, lastVisible)
        }
    }

    private fun getCharacters(lastVisible: Int) {
        subscribeFlow(
            getCharactersUseCase.invoke(lastVisible)
                .onEach { result ->
                    when (result) {
                        is ResponseWrapper.Error -> {
                            uiState.value = CharacterListUiState.ErrorState(result.error!!)
                        }
                        is ResponseWrapper.Success -> {
                            uiState.value = CharacterListUiState.SuccessState(result.data!!)
                        }
                    }
                }
        )
    }

    private fun searchCharacter(name: String, lastVisible: Int) {
        val params = mapOf<String, String>(
            Constants.SEARCH_KEY to name,
            Constants.OFFSET_KEY to lastVisible.toString()
        )
        subscribeFlow(
            searchCharactersUseCase.invoke(params)
                .onEach { result ->
                    when (result) {
                        is ResponseWrapper.Error -> {
                            uiState.value = CharacterListUiState.ErrorState(result.error!!)
                        }
                        is ResponseWrapper.Success -> {
                            uiState.value = CharacterListUiState.SuccessState(result.data!!)
                        }
                    }
                }
        )
    }
}
