package com.library.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.library.presentation.theme.Brown80
import com.library.presentation.theme.LibraryTypography


//region Preview samples
class EmptyViewUIProvider : PreviewParameterProvider<EmptyViewUI> {
    override val values = sequenceOf(
        EmptyViewUI(
            text = "Что-то пошло не так",
            hasTopSpacer = false
        ),
    )
}
//endregion

data class EmptyViewUI(
    val text: String,
    val hasTopSpacer: Boolean = true,
)

@Preview(showBackground = true)
@Composable
fun EmptyView(
    @PreviewParameter(EmptyViewUIProvider::class) uiData: EmptyViewUI,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(Color.Transparent),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.clip(MaterialTheme.shapes.small).background(Brown80)
        ) {
            if (uiData.hasTopSpacer) {
                Spacer(modifier = Modifier.weight(0.34f))
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.clip(MaterialTheme.shapes.small).background(Color.Transparent),
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    imageVector = Icons.Filled.Info,
                    modifier = Modifier.size(48.dp),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = uiData.text,
                    style = LibraryTypography.bodySmall,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

    }
}