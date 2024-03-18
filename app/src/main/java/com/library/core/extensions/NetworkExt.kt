package com.library.core.extensions

import com.library.providers.api.handlers.DataState


fun <T : Any> DataState<T>.doFinally(callback: (DataState<T>) -> Unit): DataState<T> {
    callback(this)
    return this
}

fun <T : Any> DataState<T>.doIfFailure(callback: (String) -> Unit): DataState<T> {
    if (this is DataState.Failure) {
        callback(errorMsg.orEmpty())
    }
    return this
}

fun <T : Any> DataState<T>.doIfSuccess(callback: (value: T) -> Unit): DataState<T> {
    if (this is DataState.Success) {
        callback(data)
    }
    return this
}

suspend fun <T : Any> DataState<T>.doIfSuccessSuspend(callback: suspend (value: T) -> Unit): DataState<T> {
    if (this is DataState.Success) {
        callback(data)
    }
    return this
}