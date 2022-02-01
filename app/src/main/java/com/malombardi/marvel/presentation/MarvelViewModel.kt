package com.malombardi.marvel.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class MarvelViewModel : ViewModel() {
    protected fun <T> subscribeFlow(flow: Flow<T>) {
        flow.onStart {
        }.onCompletion {
        }.flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }
}