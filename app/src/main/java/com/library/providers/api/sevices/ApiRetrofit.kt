package com.library.providers.api.sevices

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRetrofit {
    var retrofitServ: ApiServices?

    init {
        retrofitServ = null
    }

    fun setUpBaseUrl() {
        try {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://127.0.0.1:8000")
                .build()
            retrofitServ = retrofit.create(ApiServices::class.java)
        } catch (e: Exception) {
            Log.d("exception", "$e")
        }
    }
}