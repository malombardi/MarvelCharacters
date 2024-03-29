package com.malombardi.domain.usecases

import com.malombardi.domain.ResponseWrapper
import com.malombardi.domain.errors.IErrorHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, out R>(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val errorHandler: IErrorHandler
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
