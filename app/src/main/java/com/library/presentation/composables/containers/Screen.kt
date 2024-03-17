package com.library.presentation.composables.containers

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.library.presentation.BaseViewModel

data class ScaffoldUI(
    val modifier: Modifier = Modifier,
    val containerColor: Color = Color.White,
    val onFailureDetailButtonClick: () -> Unit = {},
    val topBar: @Composable () -> Unit = {},
    val bottomBar: @Composable () -> Unit = {},
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    val content: @Composable ColumnScope.() -> Unit = {},
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun <T : BaseViewModel> Screen(
    viewModel: T,
    scaffoldUI: ScaffoldUI = ScaffoldUI(),
    onScreenLaunch: () -> Unit = {},
) {
    LaunchedEffect(Unit) {
        onScreenLaunch()
    }

    val loaderUI by remember { viewModel.progressState }

    ScaffoldWithNetwork(
        modifier = scaffoldUI.modifier,
        topBar = scaffoldUI.topBar,
        bottomBar = scaffoldUI.bottomBar,
        loaderWidgetUI = loaderUI,
        containerColor = scaffoldUI.containerColor
    ) {
        scaffoldUI.content(this)
    }
}