package com.malombardi.marvel.presentation.characters.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.malombardi.marvel.domain.ResponseWrapper
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.usecases.GetCharactersUseCase
import com.malombardi.marvel.presentation.MarvelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) :
    MarvelViewModel() {

    val lastItemVisible = MutableStateFlow(0)

    val uiState = MutableStateFlow<CharacterListUiState>(CharacterListUiState.LoadingState(true))

    init {
        viewModelScope.launch {
            lastItemVisible.collect {
                notifyLastVisible(it)
            }
        }
    }

    private fun notifyLastVisible(lastVisible: Int) {
        getCharacters(lastVisible)
    }

    fun onItemSelected(marvelCharacter: MarvelCharacter) {
        uiState.value = CharacterListUiState.CharacterSelectedState(marvelCharacter)
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
}
