package com.malombardi.marvel.presentation.splash

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun setup() {
        splashViewModel = SplashViewModel()
    }

    @Test
    fun `animationFinished should change when on method call`() = runBlockingTest {
        //Given
        assert(!splashViewModel.animationFinished.value)

        //When
        splashViewModel.notifyAnimationFinished()

        //Then
        assert(splashViewModel.animationFinished.value)
    }
}