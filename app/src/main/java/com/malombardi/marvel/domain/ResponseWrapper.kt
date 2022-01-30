package com.malombardi.marvel.domain

import com.malombardi.marvel.domain.errors.ErrorEntity

/**
 * This class is used as a wrapper for each response
 */
sealed class ResponseWrapper<out T>(val data: T? = null, val error: ErrorEntity? = null) {

    class Success<T>(data: T) : ResponseWrapper<T>(data)

    class Error<T>(error: ErrorEntity, data: T? = null) : ResponseWrapper<T>(data, error)
}
