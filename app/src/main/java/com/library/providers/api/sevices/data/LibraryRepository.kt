package com.library.providers.api.sevices.data

import com.library.data.models.books.BookMetadata
import com.library.data.models.books.BookModel
import com.library.data.models.groups.Group
import com.library.data.models.responses.LoginResponseModel
import com.library.providers.api.handlers.ApiHandler
import com.library.providers.api.handlers.NetworkResult
import com.library.providers.api.sevices.ApiServices
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices
) : LibraryRepository, ApiHandler {

    private val scannedQr = MutableStateFlow("")

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


    override suspend fun getBookMetadata(isbn: String): NetworkResult<BookMetadata> =
        handleApi { apiServices.getBookMetadata(isbn = isbn) }

    override suspend fun getUserBooks(userId: String): NetworkResult<List<BookModel>> =
        handleApi { apiServices.getUserBooks(userId = userId) }

    override suspend fun addBook(
        isbn: String,
        groupId: String
    ): NetworkResult<BookModel> = handleApi {
        apiServices.addBook(isbn, groupId)
    }

    override suspend fun getGroup(groupId: String): NetworkResult<Group> = handleApi {
        apiServices.getGroup(groupId)
    }

    override suspend fun getBook(bookId: String): NetworkResult<BookModel> = handleApi {
        apiServices.getBook(bookId = bookId)
    }

    override suspend fun addGroup(
        name: String,
        parentGroupId: String,
    ): NetworkResult<Group> = handleApi {
        apiServices.addGroup(name, parentGroupId)
    }

    override suspend fun deleteGroup(
        groupId: String,
        parentGroupId: String
    ): NetworkResult<String> =
        handleApi {
            apiServices.deleteGroup(groupId, parentGroupId)
        }

    override suspend fun getUserGroups(userId: String): NetworkResult<List<Group>> = handleApi {
        apiServices.getUserGroups(userId)
    }

    override suspend fun moveBook(
        bookId: String,
        currentGroupId: String,
        targetGroupId: String
    ): NetworkResult<String> = handleApi {
        apiServices.moveBook(bookId,currentGroupId, targetGroupId)
    }

    override fun getScannedQr(): String {
        return scannedQr.value
    }

    override suspend fun updateScannedQr(code: String) {
        scannedQr.emit(code)
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

    suspend fun getBookMetadata(isbn: String): NetworkResult<BookMetadata>

    suspend fun getUserBooks(userId: String): NetworkResult<List<BookModel>>

    suspend fun addBook(isbn: String, groupId: String): NetworkResult<BookModel>

    suspend fun getGroup(
        groupId: String,
    ): NetworkResult<Group>

    suspend fun getBook(
        bookId: String,
    ): NetworkResult<BookModel>

    suspend fun addGroup(
        name: String,
        parentGroupId: String,
    ): NetworkResult<Group>

    suspend fun deleteGroup(
        groupId: String,
        parentGroupId: String,
    ): NetworkResult<String>

    suspend fun getUserGroups(
        userId: String,
    ): NetworkResult<List<Group>>

    suspend fun moveBook(
        bookId: String,
        currentGroupId: String,
        targetGroupId: String,
    ): NetworkResult<String>

    fun getScannedQr(): String

    suspend fun updateScannedQr(code: String)
}