package com.guidal.data.utils

// TODO KDoc
class DatabaseOperationUtils {
    inline fun <T> safeDatabaseOperation(operation: () -> T): DatabaseOperation<T> {
        return try {
            DatabaseOperation.Success(data = operation())
        } catch (e: Exception) {
            DatabaseOperation.Failure(exception = e)
        }
    }
}

// TODO KDoc
// TODO Unit test (1 branch)
sealed interface DatabaseOperation<T> {
    data class Success<T>(val data: T): DatabaseOperation<T>
    data class Failure<T>(val exception: Exception): DatabaseOperation<T>

    fun <R> mapSuccess(transform: (T) -> R): DatabaseOperation<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(exception)
        }
    }

    suspend fun onSuccess(block: suspend (T) -> Unit): DatabaseOperation<T> {
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (Exception) -> Unit): DatabaseOperation<T> {
        if (this is Failure) block(exception)
        return this
    }
}