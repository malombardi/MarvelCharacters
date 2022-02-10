package com.malombardi.marvel.domain.usecases

import com.malombardi.marvel.domain.errors.IErrorHandler
import com.malombardi.marvel.domain.models.MarvelCharacter
import com.malombardi.marvel.domain.repository.Repository
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
