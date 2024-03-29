package com.malombardi.domain.usecases

import com.malombardi.domain.Constants.OFFSET_KEY
import com.malombardi.domain.Constants.SEARCH_KEY
import com.malombardi.domain.errors.IErrorHandler
import com.malombardi.domain.models.MarvelCharacter
import com.malombardi.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCharactersUseCase @Inject constructor(
    private val repository: Repository,
    coroutineDispatcher: CoroutineDispatcher,
    errorHandler: IErrorHandler
) : FlowUseCase<Map<String, String>, List<MarvelCharacter>>(coroutineDispatcher, errorHandler) {

    override suspend fun execute(parameters: Map<String, String>): Flow<List<MarvelCharacter>> {
        //here I need the startWith value or throw an exception
        val result = repository.searchCharacters(parameters.getValue(SEARCH_KEY))
        if (parameters[OFFSET_KEY] != null) {
            repository.checkSearchRequireNewPage(
                parameters[SEARCH_KEY]!!,
                parameters[OFFSET_KEY]!!.toInt()
            )
        }
        return result
    }
}
