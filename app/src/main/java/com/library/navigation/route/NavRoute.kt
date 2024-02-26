package com.library.navigation.route

import android.app.Activity
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable


interface NavRoute<T : RouteNavigator> {

    val route: String

    @Composable
    fun Content(viewModel: T, windowSizeClass: WindowSizeClass)

    @Composable
    fun viewModel(): T
    fun getArguments(): List<NamedNavArgument> = listOf()
    fun composable(
        builder: NavGraphBuilder,
        navHostController: NavHostController,
        windowSizeClass: WindowSizeClass,
    ) {
        builder.composable(
            route = route,
            arguments = getArguments(),
            content = {
                val viewModel: T = viewModel()
                val viewStateAsState by viewModel.navigationState.collectAsState()

                LaunchedEffect(viewStateAsState) {
                    updateNavigationState(
                        navHostController = navHostController,
                        navigationState = viewStateAsState,
                        onNavigated = viewModel::onNavigated,
                    )
                }

                Content(viewModel, windowSizeClass)
            }
        )
    }

    private fun updateNavigationState(
        navHostController: NavHostController,
        navigationState: NavigationState,
        onNavigated: (navState: NavigationState) -> Unit,
    ) {
        when (navigationState) {
            is NavigationState.NavigateToRoute -> {
                navHostController.navigate(navigationState.route)
                onNavigated(navigationState)
            }

            is NavigationState.PopToRoute -> {
                navHostController.popBackStack(navigationState.staticRoute, false)
                onNavigated(navigationState)
            }

            is NavigationState.NavigateUp -> {
                navHostController.navigateUp()
                onNavigated(navigationState)
            }

            is NavigationState.Idle -> {
            }

            is NavigationState.NavigationCloseApp -> {
                (navHostController.context as? Activity)?.finish()
            }
        }
    }
}