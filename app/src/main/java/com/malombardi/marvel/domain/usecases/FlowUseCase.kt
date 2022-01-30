package com.malombardi.marvel.domain.usecases

import com.malombardi.marvel.domain.ResponseWrapper
import com.malombardi.marvel.domain.errors.ErrorHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, out R>(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val errorHandler: ErrorHandler
) {

    operator fun invoke(parameters: P): Flow<ResponseWrapper<R>> {
        return flow {
            try {
                execute(parameters).collect {
                    emit(ResponseWrapper.Success(it))
                }
            } catch (throwable: Throwable) {
                emit(ResponseWrapper.Error(errorHandler.getError(throwable)))
            }
        }.flowOn(coroutineDispatcher)
    }

    protected abstract suspend fun execute(parameters: P): Flow<R>
}
