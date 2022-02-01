package com.malombardi.marvel.domain

import com.malombardi.marvel.domain.errors.ErrorHandler
import com.malombardi.marvel.domain.fake_datasources.FakeLocalDataSource
import com.malombardi.marvel.domain.fake_datasources.FakeRemoteDataSource
import com.malombardi.marvel.domain.repository.Repository
import com.malombardi.marvel.domain.usecases.GetCharactersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCharactersUseCaseTest {

    private val localDataSource = FakeLocalDataSource()
    private val remoteDataSource = FakeRemoteDataSource()
    private val errorHandler = ErrorHandler()

    private val repository = Repository(localDataSource, remoteDataSource)

    @ExperimentalCoroutinesApi
    @Test
    fun `getCharacter from localDataSource when success return flow`() = runBlockingTest {
        //GIVEN

        //WHEN
        val listResults = getCharactersUseCase.invoke(0).toList()[0]

        //THEN
        assert(listResults is ResponseWrapper.Success)
        assertEquals(localDataSource.charactersSize(), listResults.data!!.size)
    }
}
