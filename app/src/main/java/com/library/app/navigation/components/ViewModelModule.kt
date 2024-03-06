package com.library.app.navigation.components

import com.library.presentation.navigation.route.RouteNavigator
import com.library.presentation.navigation.route.RouteNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun bindRouteNavigator(): RouteNavigator = RouteNavigatorImpl()
}