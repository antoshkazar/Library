package com.library.data.models.books

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookUi(
    @SerializedName("isbn")
    val isbn: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("authors")
    val authors: String = "",
    @SerializedName("publisher")
    val publisher: String = "",
    @SerializedName("year")
    val year: String = "",
    @SerializedName("language")
    val language: String = "",
): Parcelable
