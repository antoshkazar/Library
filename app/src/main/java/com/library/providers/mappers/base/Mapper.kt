package com.library.providers.mappers.base

interface Mapper<in From : Any?, out To : Any?> {
    suspend fun convert(from: From): To
}
suspend fun<From : Any, To : Any> Mapper<From, To>.convertList(fromItems: List<From>?): List<To> {
    return fromItems?.map { convert(it) }.orEmpty()
}