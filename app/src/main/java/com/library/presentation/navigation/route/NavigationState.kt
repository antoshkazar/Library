package com.library.presentation.navigation.route

import java.util.UUID

sealed class NavigationState {

    data object Idle : NavigationState()

    data class NavigateToRoute(
        val route: String,
        val id: String = UUID.randomUUID().toString(),
        val data: Any? = null
    ) : NavigationState()

    data class PopToRoute(val staticRoute: String, val id: String = UUID.randomUUID().toString()) :
        NavigationState()

    data class NavigateUp(val id: String = UUID.randomUUID().toString()) : NavigationState()
    data object NavigationCloseApp: NavigationState()
}