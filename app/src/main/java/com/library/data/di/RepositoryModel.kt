package com.library.data.di

import com.library.providers.api.sevices.ApiServices
import com.library.providers.api.sevices.HeaderInterceptor
import com.library.providers.api.sevices.LibraryRepository
import com.library.providers.api.sevices.LibraryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModel {

    @Singleton
    @Provides
    fun provideBaseUrl(): String = "http://127.0.0.1:8000";

    @Singleton
    @Provides
    fun interpt(): Interceptor {
        return HeaderInterceptor
    }

    @Singleton
    @Provides
    fun provideHttpClient(intercept: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(intercept)
            .build();
    }

    @Provides
    fun provideRetrofitInstance(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(client)
            .build()
    }

    @Provides
    fun provideLibraryRepository(apiServices: ApiServices): LibraryRepository =
        LibraryRepositoryImpl(apiServices = apiServices)
}