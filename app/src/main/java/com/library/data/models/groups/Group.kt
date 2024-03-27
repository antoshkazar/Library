package com.library.data.models.groups

import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("identifier")
    val groupIdentifier: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("subgroups_ids")
    val subgroupsIds: List<Int> = emptyList(),
    @SerializedName("books_ids")
    val booksIds: List<Int> = emptyList(),
)