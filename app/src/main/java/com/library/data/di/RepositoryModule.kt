package com.library.data.di

import com.library.BuildConfig
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
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_URL = "http://192.168.2.48:8000"

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

    @Provides
    fun provideRetrofitInstance(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit {
        val httpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                this.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideLibraryRepository(apiServices: ApiServices): LibraryRepository =
        LibraryRepositoryImpl(apiServices = apiServices)
}