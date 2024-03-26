package com.library.data.models.books

import com.google.gson.annotations.SerializedName

data class BookResponseModel(
    @SerializedName("identifier")
    val identifier: String = "",
    @SerializedName("metadata")
    val metadata: BookUi = BookUi(),
    @SerializedName("comment")
    val comment: String = "",
    @SerializedName("color")
    val color: String = "",
    @SerializedName("place")
    val place: String = "",
)