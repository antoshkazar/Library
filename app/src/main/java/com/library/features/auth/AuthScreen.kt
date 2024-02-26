package com.library.features.auth

import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    windowSizeClass: WindowSizeClass,
) {
    Text(text = "hello world!")
}