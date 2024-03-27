package com.library.core.extensions

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.library.data.models.books.BookMetadata
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

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

val ruLocale = java.util.Locale("ru_Ru")

fun Boolean?.orTrue(): Boolean = this ?: true
fun Boolean?.orFalse(): Boolean = this ?: false
fun Boolean.toInt() = if (this) 1 else 0
fun Int?.orZero(): Int {
    return this ?: 0
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", ruLocale).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}

fun <T> List<T>.addIf(condition: Boolean, element: T): List<T> {
    val mutableList = this.toMutableList()
    if (condition) {
        mutableList.add(element)
    }
    return mutableList
}

fun <T> List<T>.addToList(element: T): List<T> {
    val mutableList = this.toMutableList()
    mutableList.add(element)
    return mutableList
}

fun <T> List<T>.addAllToList(element: List<T>): List<T> {
    val mutableList = this.toMutableList()
    mutableList.addAll(element)
    return mutableList
}

fun <T> List<T>.replaceAllInList(element: List<T>): List<T> {
    val mutableList = this.toMutableList()
    element.forEach { newItem ->
        if (!mutableList.contains(newItem)) {
            mutableList.add(newItem)
        }
    }

    return mutableList
}

fun List<BookMetadata>.containsBook(bookUi: BookMetadata): Boolean =
    this.any { it.isbn == bookUi.isbn }