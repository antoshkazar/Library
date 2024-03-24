package com.library.data.models.groups

data class Group(
    val identifier: String = "",
    val name: String = "",
    val subgroupsIds: List<Int> = emptyList(),
    val booksIds: List<Int> = emptyList(),
)