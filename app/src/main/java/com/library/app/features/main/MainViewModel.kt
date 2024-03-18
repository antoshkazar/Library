package com.library.app.features.main

import com.library.presentation.BaseViewModel
import com.library.presentation.navigation.route.RouteNavigator
import com.library.providers.api.sevices.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val libraryRepository: LibraryRepository,
) : BaseViewModel(), RouteNavigator by routeNavigator {

}