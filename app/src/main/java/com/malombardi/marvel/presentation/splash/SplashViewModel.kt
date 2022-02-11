package com.malombardi.marvel.presentation.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel: ViewModel() {

    private val _animationFinished = MutableStateFlow(false)
    val animationFinished : StateFlow<Boolean> get() = _animationFinished

    fun notifyAnimationFinished(){
        _animationFinished.value = true
    }
}
