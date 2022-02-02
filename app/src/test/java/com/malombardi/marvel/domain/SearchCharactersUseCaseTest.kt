package com.malombardi.marvel.domain

import com.malombardi.marvel.domain.Constants.SEARCH_KEY
import com.malombardi.marvel.domain.errors.ErrorHandler
import com.malombardi.marvel.domain.fake_datasources.FakeLocalDataSource
import com.malombardi.marvel.domain.fake_datasources.FakeRemoteDataSource
import com.malombardi.marvel.domain.repository.Repository
import com.malombardi.marvel.domain.usecases.SearchCharactersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchCharactersUseCaseTest {

    private val localDataSource = FakeLocalDataSource()
    private val remoteDataSource = FakeRemoteDataSource()
    private val errorHandler = ErrorHandler()

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()
    private val repository = Repository(localDataSource, remoteDataSource)

    @ExperimentalCoroutinesApi
    private val searchCharactersUseCase =
        SearchCharactersUseCase(repository, coroutineDispatcher, errorHandler)

    @ExperimentalCoroutinesApi
    @Test
    fun `searchCharacter from localDataSource when success return flow`() = runBlocking {
        //GIVEN
        val textToSearch = "FAKE"

        //WHEN
        val options = mapOf(SEARCH_KEY to textToSearch)
        val listResults = searchCharactersUseCase.invoke(options).toList()[0]

        //THEN
        assert(listResults is ResponseWrapper.Success)
        assertEquals(localDataSource.charactersSize(), listResults.data!!.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `searchCharacter from localDataSource when no textToSearch return error`() = runBlockingTest {
        //GIVEN

        //WHEN
        val options = HashMap<String,String>()
        val listResults = searchCharactersUseCase.invoke(options).toList()[0]

        //THEN
        assert(listResults is ResponseWrapper.Error)
    }
}
