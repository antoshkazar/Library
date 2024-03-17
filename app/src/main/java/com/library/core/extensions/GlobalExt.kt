package com.library.core.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun String.toSingleTrimIndent(): String {
    return trimIndent().replace("\n", "")
}

fun <T> String.fromJson(type: Class<T>): T {
    return Gson().fromJson(this, type)
}

inline fun <reified T> String.fromJson(): T? =
    Gson().fromJson<T>(this, object : TypeToken<T>() {}.type)

fun Any.toJson(): String {
    return Gson().toJson(this)
}

fun Boolean?.orTrue(): Boolean = this ?: true
fun Boolean?.orFalse(): Boolean = this ?: false
fun Boolean.toInt() = if (this) 1 else 0
fun Int?.orZero(): Int {
    return this ?: 0
}