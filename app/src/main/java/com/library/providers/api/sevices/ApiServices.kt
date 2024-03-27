package com.library.providers.api.sevices

import com.library.data.models.books.BookResponseModel
import com.library.data.models.books.BookUi
import com.library.data.models.groups.Group
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
    ): Response<List<BookResponseModel>>

    @GET("/add_book")
    suspend fun addBook(
        @Query("isbn") isbn: String,
        @Query("group_id") groupId: String,
    ): Response<BookResponseModel>

    @GET("/add_group")
    suspend fun addGroup(
        @Query("name") name: String,
        @Query("parent_group_id") parentGroupId: String,
    ): Response<Group>

    @GET("/get_group")
    suspend fun getGroup(
        @Query("group_id") groupId: String,
    ): Response<Group>

    @GET("/get_book")
    suspend fun getBook(
        @Query("book_id") bookId: String,
    ): Response<BookResponseModel>

    @GET("/delete_group")
    suspend fun deleteGroup(
        @Query("group_id") groupId: String,
        @Query("parent_group_id") parentGroupId: String,
    ): Response<String>

}