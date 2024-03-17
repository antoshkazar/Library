package com.library.app.features.auth

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.library.presentation.composables.containers.ScaffoldUI
import com.library.presentation.composables.containers.Screen

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    windowSizeClass: WindowSizeClass,
) {

    val localFocusManager = LocalFocusManager.current
    Screen(
        viewModel = viewModel,
        scaffoldUI = ScaffoldUI(
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            },
            content = {
                Column(modifier = Modifier.fillMaxSize())
                {

                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = viewModel::onButtonClick,
                    ) {
                        Text(text = "Вызов")
                    }
                }
            }
        )
    )
}