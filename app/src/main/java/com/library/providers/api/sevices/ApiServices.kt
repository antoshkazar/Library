package com.library.providers.api.sevices

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("/create_user")
    suspend fun createClient(
        @Query("name") name: String,
        @Query("login") login: String,
        @Query("password") password: String
    ): Response<Any>

    @GET("/get_user")
    suspend fun getUser(@Query("user_id") userId: String): Response<Any>

    @GET("/get_user_by_login")
    suspend fun getUserByLogin(
        @Query("login") login: String,
        @Query("password") password: String
    ): Response<Any>

    @GET("/book_metadata")
    suspend fun getBookMetadata(
        @Query("isbn") isbn: String,
    ): Response<Any>
}