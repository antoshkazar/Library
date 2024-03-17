package com.library.presentation.navigation.route

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface RouteNavigator {

    fun closeApp()
    fun onNavigated(state: NavigationState)

    /**
     * Вернуться вверх по стеку экранов на 1 шаг
     */
    fun navigateUp()

    /**
     * Вернуться вверх по стеку экранов до указанного route
     */
    fun popToRoute(route: String)
    fun navigateToRoute(route: String)

    val navigationState: StateFlow<NavigationState>
}

class RouteNavigatorImpl : RouteNavigator {

    override val navigationState: MutableStateFlow<NavigationState> =
        MutableStateFlow(NavigationState.Idle)

    override fun onNavigated(state: NavigationState) {
        // clear navigation state, if state is the current state:
        navigationState.compareAndSet(state, NavigationState.Idle)
    }

    override fun closeApp() = navigate(NavigationState.NavigationCloseApp)
    override fun popToRoute(route: String) = navigate(NavigationState.PopToRoute(route))
    override fun navigateUp() = navigate(NavigationState.NavigateUp())

    override fun navigateToRoute(route: String) = navigate(NavigationState.NavigateToRoute(route))

    @VisibleForTesting
    fun navigate(state: NavigationState) {
        navigationState.value = state
    }
}