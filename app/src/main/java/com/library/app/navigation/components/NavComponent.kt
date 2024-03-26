package com.library.app.navigation.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.library.app.navigation.route.AuthRoute
import com.library.app.navigation.route.BookRoute
import com.library.app.navigation.route.GroupRoute
import com.library.app.navigation.route.MainRoute

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
        MainRoute.composable(this, navHostController, windowSize)
        BookRoute.composable(this, navHostController, windowSize)
        GroupRoute.composable(this, navHostController, windowSize)
    }
}