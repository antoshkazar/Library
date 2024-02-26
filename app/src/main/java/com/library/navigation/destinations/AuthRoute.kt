package com.library.navigation.destinations

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.library.features.auth.AuthScreen
import com.library.features.auth.AuthViewModel
import com.library.navigation.route.NavRoute

object AuthRoute : NavRoute<AuthViewModel> {
    override val route = "auth_screen"
    @Composable
    override fun viewModel(): AuthViewModel = hiltViewModel()

    @Composable
    override fun Content(
        viewModel: AuthViewModel,
        windowSizeClass: WindowSizeClass,
    ) = AuthScreen(viewModel, windowSizeClass)
}