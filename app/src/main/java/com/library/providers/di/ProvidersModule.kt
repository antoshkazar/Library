package com.library.providers.di

import android.content.Context
import com.library.providers.auth.AuthPreference
import com.library.providers.auth.AuthPreferenceImpl
import com.library.providers.fileProvider.FileProvider
import com.library.providers.fileProvider.FileProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvidersModule {

    @Provides
    @Singleton
    fun provideAuthPreferences(@ApplicationContext context: Context): AuthPreference =
        AuthPreferenceImpl(context)

    @Provides
    @Singleton
    fun provideFileProvider(
        @ApplicationContext context: Context,
    ): FileProvider = FileProviderImpl(context = context)
}