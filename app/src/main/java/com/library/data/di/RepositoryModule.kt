package com.library.data.di

import com.library.providers.api.sevices.ApiServices
import com.library.providers.api.sevices.HeaderInterceptor
import com.library.providers.api.sevices.data.LibraryRepository
import com.library.providers.api.sevices.data.LibraryRepositoryImpl
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


const val BASE_URL = "http://127.0.0.1:8000"

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideBaseUrl(): String = BASE_URL;

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
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
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun provideLibraryRepository(apiServices: ApiServices): LibraryRepository =
        LibraryRepositoryImpl(apiServices = apiServices)
}