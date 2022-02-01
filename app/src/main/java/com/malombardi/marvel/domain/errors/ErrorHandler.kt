package com.malombardi.marvel.domain.errors

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class ErrorHandler : IErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.NetworkError
            is HttpException -> {
                getDomainError(throwable)
            }
            else -> {
                ErrorEntity.InternalError(
                    THROWABLE_ERROR_CODE,
                    throwable.message ?: ""
                )
            }
        }
    }

    private fun getDomainError(throwable: HttpException): ErrorEntity {
        val errorResponse = convertErrorBody(throwable)
        return ErrorEntity.InternalError(null, errorResponse ?: "")
    }

    private fun convertErrorBody(throwable: HttpException): String? {
        return try {
            val string = throwable.response()?.errorBody()?.string()

            return if (JSONObject(string).has("status")) {
                JSONObject(string).getJSONObject("status").toString()
            } else {
                string
            }
        } catch (exception: Exception) {
            null
        }
    }

    companion object {
        const val EMPTY_DATA_ERROR_CODE = 1
        const val THROWABLE_ERROR_CODE = 2
        const val UNKNOWN_ERROR_CODE = 3
    }
}
