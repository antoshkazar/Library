package com.library.providers.api.sevices.data

import com.library.data.models.books.AddBookResponseModel
import com.library.data.models.books.BookUi
import com.library.data.models.responses.LoginResponseModel
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
    ): NetworkResult<LoginResponseModel> {
        return handleApi {
            apiServices.createClient(
                name = name,
                login = login,
                password = password
            )
        }
    }


    override suspend fun getUserByLogin(
        login: String,
        password: String
    ): NetworkResult<LoginResponseModel> =
        handleApi { apiServices.getUserByLogin(login = login, password = password) }


    override suspend fun getBookMetadata(isbn: String): NetworkResult<BookUi> =
        handleApi { apiServices.getBookMetadata(isbn = isbn) }

    override suspend fun getUserBooks(userId: String): NetworkResult<List<AddBookResponseModel>> =
        handleApi { apiServices.getUserBooks(userId = userId) }

    override suspend fun addBook(
        isbn: String,
        groupId: String
    ): NetworkResult<AddBookResponseModel> = handleApi {
        apiServices.addBook(isbn, groupId)
    }

}

interface LibraryRepository {

    suspend fun getUserByLogin(
        login: String,
        password: String
    ): NetworkResult<LoginResponseModel>

    suspend fun createClient(
        name: String,
        login: String,
        password: String
    ): NetworkResult<LoginResponseModel>

    suspend fun getBookMetadata(isbn: String): NetworkResult<BookUi>

    suspend fun getUserBooks(userId: String): NetworkResult<List<AddBookResponseModel>>

    suspend fun addBook(isbn: String, groupId: String): NetworkResult<AddBookResponseModel>
}