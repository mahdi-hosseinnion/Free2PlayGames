package com.ssmmhh.free2playgames.feature_game.data.util

import com.ssmmhh.free2playgames.feature_game.data.util.Result.Success

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success]
 */
val Result<*>.succeeded
    get() = this is Success

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeededAndDataIsNotNull
    get() = this is Success && data != null