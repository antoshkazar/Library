package com.library.core.extensions

fun String.toSingleTrimIndent(): String {
    return trimIndent().replace("\n", "")
}