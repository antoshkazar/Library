package com.library.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.library.R
import com.library.presentation.theme.Brown80
import com.library.presentation.theme.LibraryTypography

//region Preview samples
class TopBarUIProvider : PreviewParameterProvider<TopBarUI> {
    override val values = sequenceOf(
        TopBarUI(title = "Авторизация", hasBackButton = false),
        TopBarUI(
            title = "Контроль качества. v1.1.15",
            titleIconRes = R.drawable.ic32_back,
        ),
        TopBarUI(
            title = "Пример с прогрессом",
            titleIconRes = R.drawable.ic32_back,
        ),
    )
}
//endregion

enum class TopBarMode {
    DEFAULT, CONTAINER
}

data class TopBarUI(
    val title: String = "",
    val titleIconRes: Int? = null,
    val hasBackButton: Boolean = false,
    val mode: TopBarMode = TopBarMode.DEFAULT,
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    uiData: TopBarUI,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    onClickBackButton: () -> Unit = {},
    containerContent: @Composable () -> Unit = {},
    extraNameContent: @Composable (() -> Unit)? = null,
) {
    val isBackButtonMode = uiData.hasBackButton || uiData.mode == TopBarMode.CONTAINER

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.background(Brown80),
    ) {
        TopAppBar(
            //WindowInsets with zeros needed for prevent too much size of topBar
            windowInsets = WindowInsets(0, 0, 0, 0),
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        when (uiData.mode) {
                            TopBarMode.CONTAINER -> containerContent()
                            TopBarMode.DEFAULT -> DefaultToolbarTitle(
                                modifier = Modifier.weight(1f),
                                windowSizeClass = windowSizeClass,
                                startIconResId = uiData.titleIconRes,
                                title = uiData.title,
                                extraNameContent = extraNameContent,
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                if (isBackButtonMode) {
                    IconButton(
                        onClick = onClickBackButton,
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.ic32_back
                            ),
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(color = Color.Black),
                            contentDescription = "back button image",
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Brown80
            ),
        )
    }
}

@Composable
private fun DefaultToolbarTitle(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    startIconResId: Int? = null,
    title: String,
    extraNameContent: @Composable (() -> Unit)? = null,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        startIconResId?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = "title icon image",
                modifier = Modifier.size(32.dp),
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        Column {
            Text(
                text = title,
                style = LibraryTypography.bodyMedium,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        extraNameContent?.invoke()
    }
}