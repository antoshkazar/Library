package com.library.providers.api.sevices

import com.library.providers.api.handlers.ApiHandler
import com.library.providers.api.handlers.NetworkResult
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices
) : LibraryRepository, ApiHandler {
    override suspend fun createClient(): NetworkResult<String> {
        return handleApi { apiServices.createClient() }
    }

    override suspend fun toggleServer(): NetworkResult<Any> {
        return handleApi { apiServices.toggleServer() }
    }
}

interface LibraryRepository {

    suspend fun toggleServer(): NetworkResult<Any>
    suspend fun createClient(): NetworkResult<String>
}