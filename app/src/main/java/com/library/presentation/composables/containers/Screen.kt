package com.library.presentation.composables.containers

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults.DragHandle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.lenta.core.analytics_logs_objects.ErrorScreenDetailsAnalytics
import com.lenta.core.extensions.addClickLoggPrefix
import com.lenta.core.extensions.logg
import com.lenta.presentation.base.BaseViewModel
import com.lenta.presentation.composables.QualitySnackbar
import com.lenta.presentation.composables.QualitySnackbarDefaults
import com.lenta.presentation.composables.SyncStatusBar
import com.lenta.presentation.composables.SyncStatusBarUi
import com.lenta.presentation.dialogs.FailureDialog
import com.lenta.presentation.dialogs.QuestionDialog
import com.lenta.presentation.dialogs.WarningDialog
import com.lenta.presentation.theme.HawkesBlue
import com.lenta.presentation.utils.LifeCycleDisposableEffect
import com.lenta.presentation.utils.PreviewHelper.TSD_UROVO_P8100_DP_SIZE
import com.library.presentation.BaseViewModel


data class BottomSheetUI(
    val modifier: Modifier = Modifier,
    val isShowWhen: Boolean = false,
    val onDismissBottomSheet: () -> Unit = {},
    val scrimColor: Color = Color.White,
    val sheetBackgroundColor: Color = Color.White,
    val sheetShape: Shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    val contentModalSheet: @Composable () -> Unit = {}
)

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
    typeScreen: ScreenType = ScreenType.SCAFFOLD,
    scaffoldUI: ScaffoldUI = ScaffoldUI(),
    onScreenLaunch: () -> Unit = {},
    onPause: () -> Unit = {},
    bottomSheetUi: BottomSheetUI = BottomSheetUI(),
) {
    LaunchedEffect(Unit) {
        onScreenLaunch()
    }

   // val loaderUI by remember { viewModel.progressState }

    ScaffoldWithNetwork(
        modifier = scaffoldUI.modifier,
        windowSizeClass = windowSizeClass,
        topBar = scaffoldUI.topBar,
        bottomBar = scaffoldUI.bottomBar,
        snackbarHost = {
            SnackbarHost(scaffoldUI.snackbarHostState) {
                QualitySnackbar(
                    snackBarUI = QualitySnackbarDefaults(),
                    snackbarData = it
                )
            }
        },
        loaderWidgetUI = loaderUI,
        getCurrentEventDescription = viewModel.getCurrentEventDescription(),
        onLoaderTimerTick = viewModel::onLoaderTimerTick,
        onLoaderCancelClick = viewModel::onLoaderCancelClick,
        containerColor = scaffoldUI.containerColor
    ) {
        SyncStatusBar(
            windowSizeClass = windowSizeClass,
            syncStatusBarUi = SyncStatusBarUi(
                syncStatus = syncStatus,
                leftToSync = currentSyncQueueSize
            )
        )
        scaffoldUI.content(this)
    }
}

ScreenType.MODAL_BOTTOM_SCAFFOLD -> {
    val bottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = scaffoldUI.modifier,
        topBar = scaffoldUI.topBar,
        bottomBar = scaffoldUI.bottomBar,
        snackbarHost = {
            SnackbarHost(scaffoldUI.snackbarHostState) {
                QualitySnackbar(
                    snackBarUI = QualitySnackbarDefaults(),
                    snackbarData = it
                )
            }
        },
        containerColor = scaffoldUI.containerColor
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            scaffoldUI.content(this)
        }
    }

    if (bottomSheetUi.isShowWhen) {
        ModalBottomSheet(
            onDismissRequest = {
                bottomSheetUi.onDismissBottomSheet()
            },
            modifier = bottomSheetUi.modifier,
            sheetState = bottomSheetState,
            shape = bottomSheetUi.sheetShape,
            containerColor = bottomSheetUi.sheetBackgroundColor,
            scrimColor = bottomSheetUi.scrimColor,
            dragHandle = {
                DragHandle(
                    shape = RoundedCornerShape(6.dp),
                    height = 8.dp,
                    width = 110.dp,
                    color = HawkesBlue,
                )
            },
            content = {
                bottomSheetUi.contentModalSheet()
            }
        )
    }
}

ScreenType.LOADING_MODAL_BOTTOM_SCAFFOLD -> {
    val bottomSheetState = rememberModalBottomSheetState()
    val loaderUI by remember { viewModel.progressState }

    ScaffoldWithNetwork(
        modifier = scaffoldUI.modifier,
        windowSizeClass = windowSizeClass,
        topBar = scaffoldUI.topBar,
        bottomBar = scaffoldUI.bottomBar,
        snackbarHost = {
            SnackbarHost(scaffoldUI.snackbarHostState) {
                QualitySnackbar(
                    snackBarUI = QualitySnackbarDefaults(),
                    snackbarData = it
                )
            }
        },
        loaderWidgetUI = loaderUI,
        onLoaderTimerTick = viewModel::onLoaderTimerTick,
        onLoaderCancelClick = viewModel::onLoaderCancelClick,
        containerColor = scaffoldUI.containerColor,
    ) {
        scaffoldUI.content(this)
    }

    if (bottomSheetUi.isShowWhen) {
        ModalBottomSheet(
            onDismissRequest = {
                bottomSheetUi.onDismissBottomSheet()
            },
            modifier = bottomSheetUi.modifier,
            sheetState = bottomSheetState,
            shape = bottomSheetUi.sheetShape,
            containerColor = bottomSheetUi.sheetBackgroundColor,
            scrimColor = bottomSheetUi.scrimColor,
            dragHandle = {
                DragHandle(
                    shape = RoundedCornerShape(6.dp),
                    height = 8.dp,
                    width = 110.dp,
                    color = HawkesBlue,
                )
            },
            content = {
                bottomSheetUi.contentModalSheet()
            }
        )
    }
}
}
}

enum class ScreenType {
    LOADING_SCAFFOLD,
    SCAFFOLD,

    //TODO: Legacy, нигде не используются, подумать над выпиливанием
    MODAL_BOTTOM_SCAFFOLD,
    LOADING_MODAL_BOTTOM_SCAFFOLD,
}