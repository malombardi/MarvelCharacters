package com.malombardi.marvel.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
open class MarvelViewModel @Inject constructor() : ViewModel() {
    protected fun <T> subscribeFlow(flow: Flow<T>) {
        flow.onStart {
        }.onCompletion {
        }.flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }
}