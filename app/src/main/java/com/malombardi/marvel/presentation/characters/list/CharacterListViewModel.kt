package com.malombardi.marvel.presentation.characters.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malombardi.marvel.domain.ResponseWrapper
import com.malombardi.marvel.domain.errors.ErrorEntity
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    private val _spinner = MutableStateFlow(true)
    val spinner: StateFlow<Boolean> get() = _spinner

    val lastItemVisible = MutableStateFlow(0)

    val character: Flow<List<MarvelCharacter>> get() = repository.getCharacters()

    init {
        viewModelScope.launch {
            lastItemVisible.collect {
                notifyLastVisible(it)
            }
        }
    }

    private suspend fun notifyLastVisible(lastVisible: Int) {
        repository.checkCharactersRequireNewPage(lastVisible)
        _spinner.value = false
    }

    fun processError(error: ErrorEntity?){

    }

    fun onItemSelected(marvelCharacter: MarvelCharacter) {

    }

}