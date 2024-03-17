package com.library.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.library.R
import com.library.presentation.theme.LibraryTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.time.Duration.Companion.seconds

//region Preview samples
class LoaderWidgetStateProvider : PreviewParameterProvider<LoaderWidgetState> {
    override val values: Sequence<LoaderWidgetState> = sequenceOf(
        LoaderWidgetState(isLoading = false),
        LoaderWidgetState(isLoading = true),
        LoaderWidgetState(
            isLoading = true,
            loaderText = "Загружаем по чуть чуть",
            isShowTimer = true,
            isCancelable = true,
        ),
    )
}
//endregion

data class LoaderWidgetState(
    val isLoading: Boolean,
    val loaderText: String = "",
    val initTimerValueInSecond: Int = 0,
    val isShowTimer: Boolean = false,
    val isCancelable: Boolean = false,
)

@Preview(showBackground = true)
@Composable
fun LoaderWidget(
    @PreviewParameter(LoaderWidgetStateProvider::class) state: LoaderWidgetState,
    getCurrentEventDescription: Flow<String> = emptyFlow(),
    getPaddingTop: () -> Dp = { 0.dp },
    onTimerTick: (tick: Int) -> Unit = { },
    onCancelClick: () -> Unit = { },
) {
    val currentAppAction = getCurrentEventDescription.collectAsState(initial = "")

    var ticks by remember { mutableIntStateOf(state.initTimerValueInSecond) }
    LaunchedEffect(state.isShowTimer) {
        ticks = state.initTimerValueInSecond
        while (state.isShowTimer) {
            delay(1.seconds)
            //plus 1 because we start from 0 and 1 second is already passed
            onTimerTick(ticks + 1)
            ticks++
        }
    }

    var dots by remember { mutableStateOf("") }
    var isAppendDots by remember { mutableStateOf(true) }
    LaunchedEffect(dots) {
        delay(500L)
        if (isAppendDots) {
            if (dots.length >= 3) {
                isAppendDots = false
                dots = dots.dropLast(1)
            } else {
                dots += "."
            }
        } else {
            if (dots.isEmpty()) {
                isAppendDots = true
                dots = "."
            } else {
                dots = dots.dropLast(1)
            }
        }
    }

    if (state.isLoading) {
        Surface(color = Color.Transparent) {
            Column {
                Box(
                    modifier = Modifier
                        .height(getPaddingTop())
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.7f))
                )
                //todo ProgressIndicator()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 4.dp)
                        .background(Color.White.copy(alpha = 0.9f))
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = state.loaderText.ifBlank { stringResource(id = R.string.loading) },
                            style = LibraryTypography.headlineMedium,
                        )
                        if (currentAppAction.value.isNotEmpty()) {
                            Box {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = currentAppAction.value + dots,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}