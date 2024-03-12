package com.library.app.features.auth

import android.util.Log
import androidx.lifecycle.viewModelScope
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

    fun onButtonClick() {
        viewModelScope.launch {
            val str = libraryRepository.createClient().convertToDataState()
            Log.d("hoe3", str.toString())
        }
    }
}