package com.library.data.models.books

import com.google.gson.annotations.SerializedName

data class BookModel(
    @SerializedName("identifier")
    val identifier: String = "",
    @SerializedName("metadata")
    val metadata: BookMetadata = BookMetadata(),
    @SerializedName("comment")
    val comment: String = "",
    @SerializedName("color")
    val color: String = "",
    @SerializedName("place")
    val place: String = "",
)