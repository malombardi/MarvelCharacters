package com.malombardi.domain

import com.malombardi.domain.fake_datasources.FakeErrorHandler
import com.malombardi.domain.fake_datasources.FakeLocalDataSource
import com.malombardi.domain.fake_datasources.FakeRemoteDataSource
import com.malombardi.domain.repository.Repository
import com.malombardi.domain.usecases.GetCharactersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCharactersUseCaseTest {

    private val localDataSource = FakeLocalDataSource()
    private val remoteDataSource = FakeRemoteDataSource()
    private val errorHandler = FakeErrorHandler()

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()
    private val repository = Repository(localDataSource, remoteDataSource)

    @ExperimentalCoroutinesApi
    private val getCharactersUseCase =
        GetCharactersUseCase(repository, coroutineDispatcher, errorHandler)

    @ExperimentalCoroutinesApi
    @Test
    fun `getCharacter from localDataSource when success return flow`() = runBlocking {
        //GIVEN

        //WHEN
        val listResults = getCharactersUseCase.invoke(0).toList()[0]

        //THEN
        assert(listResults is ResponseWrapper.Success)
        assertEquals(localDataSource.charactersSize(), listResults.data!!.size)
    }
}
