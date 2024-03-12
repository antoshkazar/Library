package com.library.data.di

import com.library.providers.api.sevices.ApiServices
import com.library.providers.api.sevices.LibraryRepository
import com.library.providers.api.sevices.LibraryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModel {

    @Provides
    fun provideLibraryRepository(apiServices: ApiServices): LibraryRepository =
        LibraryRepositoryImpl(apiServices = apiServices)
}