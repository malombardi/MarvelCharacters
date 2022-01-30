package com.malombardi.marvel.domain.errors

/**
 * Types of errors (Internal, or Network)
 */
sealed class ErrorEntity {

    data class InternalError(val code: Int? = null, val message: String) : ErrorEntity()

    object NetworkError : ErrorEntity()
}
