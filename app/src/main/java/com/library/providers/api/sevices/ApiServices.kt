package com.library.providers.api.sevices

import com.library.data.models.books.BookMetadata
import com.library.data.models.books.BookModel
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
    ): Response<BookMetadata>

    @GET("/get_user_books")
    suspend fun getUserBooks(
        @Query("user_id") userId: String,
    ): Response<List<BookModel>>

    @GET("/add_book")
    suspend fun addBook(
        @Query("isbn") isbn: String,
        @Query("group_id") groupId: String,
    ): Response<BookModel>

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
    ): Response<BookModel>

    @GET("/delete_group")
    suspend fun deleteGroup(
        @Query("group_id") groupId: String,
        @Query("parent_group_id") parentGroupId: String,
    ): Response<String>

    @GET("/get_user_groups")
    suspend fun getUserGroups(
        @Query("user_id") userId: String,
    ): Response<List<Group>>

    @GET("/move_book")
    suspend fun moveBook(
        @Query("book_id") bookId: String,
        @Query("current_group_id") currentGroupId: String,
        @Query("target_group_id") targetGroupId: String,
    ): Response<String>

    @GET("/change_book")
    suspend fun changeBook(
        @Query("book_id") bookId: String,
        @Query("comment") comment: String,
        @Query("color") color: String,
        @Query("place") place: String,
    ): Response<BookModel>

    @GET("/delete_book")
    suspend fun deleteBook(
        @Query("book_id") bookId: String,
        @Query("group_id") groupId: String,
    ): Response<BookModel>

}