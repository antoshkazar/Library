package com.library.providers.api.sevices

import com.library.data.models.books.AddBookResponseModel
import com.library.data.models.books.BookUi
import com.library.data.models.responses.LoginResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("/create_user")
    suspend fun createClient(
        @Query("name") name: String,
        @Query("login") login: String,
        @Query("password") password: String
    ): Response<LoginResponseModel>

    @GET("/get_user_by_login")
    suspend fun getUserByLogin(
        @Query("login") login: String,
        @Query("password") password: String
    ): Response<LoginResponseModel>

    @GET("/book_metadata")
    suspend fun getBookMetadata(
        @Query("isbn") isbn: String,
    ): Response<BookUi>

    @GET("/get_user_books")
    suspend fun getUserBooks(
        @Query("user_id") userId: String,
    ): Response<List<AddBookResponseModel>>

    @GET("/add_book")
    suspend fun addBook(
        @Query("isbn") isbn: String,
        @Query("group_id") groupId: String,
    ): Response<AddBookResponseModel>
}