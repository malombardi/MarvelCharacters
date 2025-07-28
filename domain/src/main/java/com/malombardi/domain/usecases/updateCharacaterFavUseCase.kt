package com.malombardi.domain.usecases

import com.malombardi.domain.errors.IErrorHandler
import com.malombardi.domain.models.MarvelCharacter
import com.malombardi.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class updateCharacaterFavUseCase @Inject constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: Repository,
    error: IErrorHandler
) : FlowUseCase<MarvelCharacter, Int>(dispatcher, error) {

    override suspend fun execute(parameters: MarvelCharacter): Flow<Int> {

        repository.updateFavChararater(parameters)

        return flowOf(1)

    }

}