package com.library.providers.api.sevices.data

import com.library.providers.api.handlers.ApiHandler
import com.library.providers.api.handlers.NetworkResult
import com.library.providers.api.sevices.ApiServices
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices
) : LibraryRepository, ApiHandler {
    override suspend fun createClient(
        name: String,
        login: String,
        password: String
    ): NetworkResult<Any> {
        return handleApi {
            apiServices.createClient(
                name = name,
                login = login,
                password = password
            )
        }
    }

    override suspend fun getUser(userId: String): NetworkResult<Any> {
        return handleApi { apiServices.getUser(userId) }
    }

    override suspend fun getUserByLogin(login: String, password: String): NetworkResult<Any> {
        return handleApi { apiServices.getUserByLogin(login = login, password = password) }
    }
}

interface LibraryRepository {

    suspend fun getUser(userId: String): NetworkResult<Any>

    suspend fun getUserByLogin(
        login: String,
        password: String
    ): NetworkResult<Any>

    suspend fun createClient(
        name: String,
        login: String,
        password: String
    ): NetworkResult<Any>
}