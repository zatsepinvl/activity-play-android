package com.zatsepinvl.activityplay.firebase

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

const val DEFAULT_TIMEOUT_MILLIS = 10000L

suspend fun <T> Task<T>.await(timeoutMillis: Long = DEFAULT_TIMEOUT_MILLIS): T {
    // fast path
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException("Task $this was cancelled normally.")
            } else {
                @Suppress("UNCHECKED_CAST")
                result as T
            }
        } else {
            throw e
        }
    }
    return withTimeout(timeoutMillis) {
        //Explicit <T> parameter is required for complication, please don't remove
        suspendCancellableCoroutine<T> { cont ->
            addOnCompleteListener {
                val e = exception
                if (e == null) {
                    @Suppress("UNCHECKED_CAST")
                    if (isCanceled) cont.cancel() else cont.resume(result as T)
                } else {
                    cont.resumeWithException(e)
                }
            }
        }
    }
}