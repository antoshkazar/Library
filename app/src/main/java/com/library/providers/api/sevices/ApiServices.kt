package com.library.providers.api.sevices

import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {
    @GET("/create_user?name=test&login=test&password=test1")
    suspend fun createClient(): Response<String>
}

data class Client(
    val name: String,
    val password: String,
    val login: String,
)

