package com.library.app.features.auth

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    windowSizeClass: WindowSizeClass,
) {
    Button(onClick = viewModel::onButtonClick) {
        Text(text = "Вызов")
    }
}