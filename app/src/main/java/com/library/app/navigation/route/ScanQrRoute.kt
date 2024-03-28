package com.library.app.navigation.route

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.library.app.features.scanQr.ScanQrScreen
import com.library.app.features.scanQr.ScanQrViewModel
import com.library.presentation.navigation.route.NavRoute

object ScanQrRoute : NavRoute<ScanQrViewModel> {
    override val route = "scan_qr_screen"

    @Composable
    override fun viewModel(): ScanQrViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: ScanQrViewModel, windowSizeClass: WindowSizeClass) =
        ScanQrScreen(viewModel)
}