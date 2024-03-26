package com.library.app.navigation.route

import android.net.Uri
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.library.app.features.categories.GroupsScreen
import com.library.app.features.categories.GroupsViewModel
import com.library.core.extensions.toJson
import com.library.presentation.navigation.route.NavRoute

object GroupRoute : NavRoute<GroupsViewModel> {

    const val KEY_GROUP = "KEY_GROUP"
    override val route = "group_screen/{$KEY_GROUP}"

    fun routeWithParams(
        groupId: String,
    ): String {
        val uri = Uri.encode(
            Param(
                groupId = groupId
            ).toJson()
        )
        return route.replace("{$KEY_GROUP}", uri)
    }

    data class Param(
        val groupId: String
    )

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(KEY_GROUP) { type = NavType.StringType },
    )

    @Composable
    override fun viewModel(): GroupsViewModel = hiltViewModel()

    @Composable
    override fun Content(
        viewModel: GroupsViewModel,
        windowSizeClass: WindowSizeClass,
    ) = GroupsScreen(viewModel, windowSizeClass)
}