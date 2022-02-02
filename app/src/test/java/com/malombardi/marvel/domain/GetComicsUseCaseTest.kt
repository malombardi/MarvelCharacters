package com.malombardi.marvel.domain

import com.malombardi.marvel.domain.Constants.CHARACTER_ID_KEY
import com.malombardi.marvel.domain.errors.ErrorHandler
import com.malombardi.marvel.domain.fake_datasources.FakeLocalDataSource
import com.malombardi.marvel.domain.fake_datasources.FakeRemoteDataSource
import com.malombardi.marvel.domain.repository.Repository
import com.malombardi.marvel.domain.usecases.GetComicsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetComicsUseCaseTest {

    private val localDataSource = FakeLocalDataSource()
    private val remoteDataSource = FakeRemoteDataSource()
    private val errorHandler = ErrorHandler()

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()
    private val repository = Repository(localDataSource, remoteDataSource)

    @ExperimentalCoroutinesApi
    private val getComicsUseCase =
        GetComicsUseCase(repository, coroutineDispatcher, errorHandler)

    @ExperimentalCoroutinesApi
    @Test
    fun `getComics from localDataSource when success return flow`() = runBlocking {
        //GIVEN
        val textToSearch = "FAKE"

        //WHEN
        val options = mapOf(CHARACTER_ID_KEY to textToSearch)
        val listResults = getComicsUseCase.invoke(options).toList()[0]

        //THEN
        assert(listResults is ResponseWrapper.Success)
        assertEquals(localDataSource.charactersSize(), listResults.data!!.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getComics from localDataSource when no characterID provided return error`() =
        runBlockingTest {
            //GIVEN

            //WHEN
            val options = HashMap<String, String>()
            val listResults = getComicsUseCase.invoke(options).toList()[0]

            //THEN
            assert(listResults is ResponseWrapper.Error)
        }
}
