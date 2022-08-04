package com.malombardi.marvel.presentation.comics

import androidx.lifecycle.viewModelScope
import com.malombardi.data.ErrorHandler.Companion.EMPTY_DATA_ERROR_CODE
import com.malombardi.domain.Constants.CHARACTER_ID_KEY
import com.malombardi.domain.Constants.OFFSET_KEY
import com.malombardi.domain.Constants.UNKNOWN_ID
import com.malombardi.domain.ResponseWrapper
import com.malombardi.domain.errors.ErrorEntity
import com.malombardi.domain.usecases.GetComicsUseCase
import com.malombardi.marvel.presentation.MarvelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersComicsViewModel @Inject constructor(private val getComicsUseCase: GetComicsUseCase) :
    MarvelViewModel() {

    private var characterId: String = UNKNOWN_ID.toString()
    val lastItemVisible = MutableStateFlow(0)

    val uiState = MutableStateFlow<CharacterComicsUiState>(CharacterComicsUiState.Loading)

    fun startFetching(marvelCharacterId: String?) {
        if (marvelCharacterId.isNullOrBlank()){
            uiState.value = CharacterComicsUiState.Error(ErrorEntity.InternalError(EMPTY_DATA_ERROR_CODE))
        } else {
            characterId = marvelCharacterId
            viewModelScope.launch {
                lastItemVisible.collect {
                    notifyLastVisible(it)
                }
            }
        }
    }

    private fun notifyLastVisible(lastVisible: Int) {
        getCharacters(lastVisible)
    }

    private fun getCharacters(lastVisible: Int) {
        subscribeFlow(
            getComicsUseCase.invoke(
                mapOf(
                    CHARACTER_ID_KEY to characterId,
                    OFFSET_KEY to lastVisible.toString()
                )
            ).onEach { result ->
                when (result) {
                    is ResponseWrapper.Error -> {
                        uiState.value = CharacterComicsUiState.Error(result.error!!)
                    }
                    is ResponseWrapper.Success -> {
                        uiState.value = CharacterComicsUiState.Success(result.data!!)
                    }
                }
            }
        )
    }
}
