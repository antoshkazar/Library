package com.library.app.features.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.library.app.navigation.route.MainRoute
import com.library.core.extensions.doIfFailure
import com.library.core.extensions.doIfSuccess
import com.library.presentation.BaseViewModel
import com.library.presentation.navigation.route.RouteNavigator
import com.library.providers.api.handlers.convertToDataState
import com.library.providers.api.sevices.data.LibraryRepository
import com.library.providers.auth.AuthPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val libraryRepository: LibraryRepository,
    private val authPreference: AuthPreference,
) : BaseViewModel(), RouteNavigator by routeNavigator {

    val password = mutableStateOf("")
    val login = mutableStateOf("")
    val registrationLogin = mutableStateOf("")
    val registrationPassword = mutableStateOf("")
    val registrationName = mutableStateOf("")

    fun onScreenLaunch() {
        login.value = authPreference.login
        password.value = "11" //TODO
    }

    fun onLoginClick() {
        viewModelScope.launch {
            libraryRepository.getUserByLogin(
                login = login.value,
                password = password.value
            ).convertToDataState().doIfSuccess {
                authPreference.saveUserData(model = it)
                navigateToRoute(MainRoute.route)
            }
        }
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            libraryRepository.createClient(
                name = registrationName.value,
                login = registrationLogin.value,
                password = registrationPassword.value
            ).convertToDataState().doIfSuccess {
                authPreference.saveUserData(model = it)
                navigateToRoute(MainRoute.route)
            }.doIfFailure {
                Log.d("Failure:", it)
            }
        }
    }

    fun onPasswordChange(value: String) {
        password.value = value
    }

    fun onLoginChange(value: String) {
        login.value = value
    }

    fun onRegistrationPasswordChange(value: String) {
        registrationPassword.value = value
    }

    fun onRegistrationLoginChange(value: String) {
        registrationLogin.value = value
    }

    fun onRegistrationNameChange(value: String) {
        registrationName.value = value
    }
}