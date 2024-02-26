package com.library.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.library.navigation.destinations.AuthRoute

@Composable
fun NavigationComponent(
    navHostController: NavHostController,
    windowSize: WindowSizeClass,
) {
    NavHost(
        navController = navHostController,
        startDestination = AuthRoute.route,
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        AuthRoute.composable(this, navHostController, windowSize)
    }
}