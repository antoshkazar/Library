package com.library.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.library.R


@Preview
@Composable
private fun PreviewToggles() {
    Row() {
        EyeToggle(checked = false)
        EyeToggle(checked = true)
    }
}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
fun EyeToggle(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        if (checked) {
            Icon(
                painter = painterResource(id = R.drawable.ic32_eye_open),
                contentDescription = "hide password",
                modifier = Modifier.size(24.dp),
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic32_eye_close),
                contentDescription = "show password",
                modifier = Modifier.size(24.dp),
            )
        }
    }
}