package com.library.providers.api.sevices.data

import com.library.data.models.books.BookResponseModel
import com.library.data.models.books.BookUi
import com.library.data.models.groups.Group
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

    override suspend fun getUserBooks(userId: String): NetworkResult<List<BookResponseModel>> =
        handleApi { apiServices.getUserBooks(userId = userId) }

    override suspend fun addBook(
        isbn: String,
        groupId: String
    ): NetworkResult<BookResponseModel> = handleApi {
        apiServices.addBook(isbn, groupId)
    }

    override suspend fun getGroup(groupId: String): NetworkResult<Group> = handleApi {
        apiServices.getGroup(groupId)
    }

    override suspend fun getBook(bookId: String): NetworkResult<BookResponseModel> = handleApi {
        apiServices.getBook(bookId = bookId)
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

    suspend fun getUserBooks(userId: String): NetworkResult<List<BookResponseModel>>

    suspend fun addBook(isbn: String, groupId: String): NetworkResult<BookResponseModel>

    suspend fun getGroup(
        groupId: String,
    ): NetworkResult<Group>

    suspend fun getBook(
        bookId: String,
    ): NetworkResult<BookResponseModel>
}