package com.library.presentation.composables.containers

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import com.library.presentation.composables.LoaderWidget
import com.library.presentation.composables.LoaderWidgetState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ScaffoldWithNetwork(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    loaderWidgetUI: LoaderWidgetState,
    getCurrentEventDescription: Flow<String> = emptyFlow(),
    onLoaderTimerTick: (tick: Int) -> Unit = {},
    onLoaderCancelClick: () -> Unit = { },
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    val localFocusManager = LocalFocusManager.current
    var paddingTopValue by remember {
        mutableStateOf(Dp.Unspecified)
    }
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        containerColor = containerColor,
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                localFocusManager.clearFocus()
            })
        }) { paddingValues ->
        paddingTopValue = paddingValues.calculateTopPadding()
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            content(this)
        }
    }
    LoaderWidget(
        state = loaderWidgetUI,
        getCurrentEventDescription = getCurrentEventDescription,
        getPaddingTop = { paddingTopValue },
        onTimerTick = onLoaderTimerTick,
        onCancelClick = onLoaderCancelClick,
    )
}