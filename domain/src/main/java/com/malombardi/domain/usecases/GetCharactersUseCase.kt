package com.malombardi.domain.usecases

import com.malombardi.domain.errors.IErrorHandler
import com.malombardi.domain.models.MarvelCharacter
import com.malombardi.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: Repository,
    coroutineDispatcher: CoroutineDispatcher,
    errorHandler: IErrorHandler
) : FlowUseCase<Int?, List<MarvelCharacter>>(coroutineDispatcher, errorHandler) {

    override suspend fun execute(parameters: Int?): Flow<List<MarvelCharacter>> {
        val result = repository.getCharacters()
        parameters?.let {
            repository.checkCharactersRequireNewPage(it)
        }
        return result
    }
}
