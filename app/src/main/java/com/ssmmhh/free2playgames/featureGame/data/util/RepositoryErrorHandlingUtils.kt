package com.ssmmhh.free2playgames.featureGame.data.util

import android.util.Log
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.featureGame.data.util.Constants.NETWORK_TIMEOUT
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException

/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */
private const val API_TAG = "safeApiCall"
suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = IO,
    apiCall: suspend () -> T
): Result<T> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(NETWORK_TIMEOUT) {
                Result.Success(apiCall.invoke())
            }
        } catch (e: TimeoutCancellationException) {
            Log.e(API_TAG, "safeApiCall :TimeoutCancellationException: ${e.message}", e)
            Result.Error(StringResException(R.string.NETWORK_TIMEOUT_ERROR))
        } catch (e: IOException) {
            Log.e(API_TAG, "safeApiCall :IOException: ${e.message}", e)
            Result.Error(StringResException(R.string.NETWORK_UNKNOWN_ERROR))
        } catch (e: HttpException) {
            Log.e(API_TAG, "safeApiCall :HttpException: ${e.message}", e)
            Result.Error(StringResException(R.string.NETWORK_CONNECTION_ERROR))
        }
    }
}
