package com.library.app.features.scanQr

import androidx.lifecycle.viewModelScope
import com.library.presentation.BaseViewModel
import com.library.presentation.navigation.route.RouteNavigator
import com.library.providers.api.sevices.data.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanQrViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val libraryRepository: LibraryRepository,
) : BaseViewModel(), RouteNavigator by routeNavigator {

    fun onCodeScanned(code: String) = viewModelScope.launch {
        libraryRepository.updateScannedQr(code)
        delay(500)
        routeNavigator.navigateUp()
    }

    fun onClickBackButton() {
        routeNavigator.navigateUp()
    }
}

