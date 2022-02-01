package com.malombardi.marvel.domain.errors

/**
 * Types of errors (Internal, or Network)
 */
sealed class ErrorEntity {

    data class InternalError(val code: Int? = null, val message: String? = null) : ErrorEntity()

    object NetworkError : ErrorEntity()
}
