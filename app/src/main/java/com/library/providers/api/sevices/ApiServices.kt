package com.library.providers.api.sevices

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {
    @POST("/create_user?name=test&login=test&password=test5")
    suspend fun createClient(): Response<String>

    @GET("/get_user?user_id=1")
    suspend fun toggleServer(): Response<Any>
}

data class Client(
    val name: String,
    val password: String,
    val login: String,
)

