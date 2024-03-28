package com.library.presentation.composables.containers

import TabBarItem
import TabView
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.library.data.models.screen.Screens
import com.library.presentation.BaseViewModel
import com.library.presentation.composables.QuestionDialog

data class ScaffoldUI(
    val modifier: Modifier = Modifier,
    val containerColor: Color = Color.White,
    val onFailureDetailButtonClick: () -> Unit = {},
    val topBar: @Composable () -> Unit = {},
    val isNeedToShowNavigationBar: Boolean = true,
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    val content: @Composable ColumnScope.() -> Unit = {},
)

@Composable
fun <T : BaseViewModel> Screen(
    viewModel: T,
    scaffoldUI: ScaffoldUI = ScaffoldUI(),
    onScreenLaunch: () -> Unit = {},
    screenType: Screens = Screens.BOOKS,
) {
    LaunchedEffect(Unit) {
        onScreenLaunch()
    }
    QuestionDialog(
        ui = viewModel.questionData.value,
        dialogOpened = viewModel.showQuestionDialog,
    )
    val loaderUI by remember { viewModel.progressState }
    val tabBarItems = listOf(
        TabBarItem(
            title = "Books",
            onIconClick = viewModel::navigateToBooks,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        TabBarItem(
            title = "Categories",
            onIconClick = viewModel::navigateToCategories,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List
        ),

        TabBarItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        ),
    )

    ScaffoldWithNetwork(
        modifier = scaffoldUI.modifier,
        topBar = scaffoldUI.topBar,
        bottomBar = {
            if (scaffoldUI.isNeedToShowNavigationBar) TabView(
                tabBarItems,
                selectedIndex = screenType.ordinal
            )
        },
        loaderWidgetUI = loaderUI,
        containerColor = scaffoldUI.containerColor
    ) {
        scaffoldUI.content(this)
    }
}