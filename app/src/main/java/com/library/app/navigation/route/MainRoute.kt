package com.library.app.navigation.route

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.library.app.features.main.MainScreen
import com.library.app.features.main.MainViewModel
import com.library.presentation.navigation.route.NavRoute

object MainRoute : NavRoute<MainViewModel> {
    override val route = "main_screen"

    @Composable
    override fun viewModel(): MainViewModel = hiltViewModel()

    @Composable
    override fun Content(
        viewModel: MainViewModel,
        windowSizeClass: WindowSizeClass,
    ) = MainScreen(viewModel, windowSizeClass)
}