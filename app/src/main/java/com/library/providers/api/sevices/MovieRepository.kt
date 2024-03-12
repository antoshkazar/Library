package com.library.providers.api.sevices

import com.library.providers.api.handlers.ApiHandler
import com.library.providers.api.handlers.NetworkResult
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(private val apiServices: ApiServices) :
    LibraryRepository {
    override suspend fun createClient(): NetworkResult<String> {
        return handleApi { apiServices.createClient() }
    }
}

interface LibraryRepository : ApiHandler {
    suspend fun createClient(): NetworkResult<String>
}