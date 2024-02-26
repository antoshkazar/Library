package com.library.features.auth

import com.library.navigation.route.RouteNavigator
import com.library.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
) : BaseViewModel(), RouteNavigator by routeNavigator {

}