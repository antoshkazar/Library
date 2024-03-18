package com.library.app.features.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.library.app.navigation.route.MainRoute
import com.library.presentation.BaseViewModel
import com.library.presentation.navigation.route.RouteNavigator
import com.library.providers.api.handlers.convertToDataState
import com.library.providers.api.sevices.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val libraryRepository: LibraryRepository,
) : BaseViewModel(), RouteNavigator by routeNavigator {

    val password = mutableStateOf("")
    val login = mutableStateOf("")

    fun onLoginClick() {
        viewModelScope.launch {
            val str = libraryRepository.getUser("1").convertToDataState()
            Log.d("toggle", str.toString())
        }
        navigateToRoute(MainRoute.route)
    }

    fun onRegisterClick() {
        
    }

    fun onPasswordChange(value: String) {
        password.value = value
    }

    fun onLoginChange(value: String) {
        login.value = value
    }
}