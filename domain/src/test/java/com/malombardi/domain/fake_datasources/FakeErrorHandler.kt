package com.malombardi.domain.fake_datasources

import com.malombardi.domain.errors.ErrorEntity
import com.malombardi.domain.errors.IErrorHandler

class FakeErrorHandler : IErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        return ErrorEntity.NetworkError
    }
}