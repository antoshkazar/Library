package com.library.app.features.books

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.library.presentation.composables.containers.ScaffoldUI
import com.library.presentation.composables.containers.Screen
import com.library.presentation.theme.Brown20
import com.library.presentation.theme.Brown30
import com.library.presentation.theme.Brown60
import com.library.presentation.theme.LibraryTypography

class BookScreen {
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    viewModel: BookViewModel,
    windowSizeClass: WindowSizeClass,
) {
    val currentBookUi by remember {
        viewModel.currentBookUi
    }

    Screen(
        viewModel = viewModel,
        scaffoldUI = ScaffoldUI(
            isNeedToShowNavigationBar = true,
            content = {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = currentBookUi.title,
                        style = LibraryTypography.titleLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    ClippedTextField(
                        text = "ISBN: ${currentBookUi.isbn}",
                        backgroundColor = Brown30
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    LabelAssignmentView(
                        label = "Authors",
                        text = currentBookUi.authors,
                        backgroundColor = Brown60
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    LabelAssignmentView(
                        label = "Language",
                        text = currentBookUi.language,
                        backgroundColor = Brown20
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    LabelAssignmentView(label = "Year", text = currentBookUi.year)
                }
            }
        )
    )
}

@Composable
@Preview
private fun ClippedTextField(
    modifier: Modifier = Modifier,
    text: String = "ISBN: 174389013498u8190",
    backgroundColor: Color = Color.White,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor),
        shadowElevation = 9.dp,
    ) {
        Column(modifier = Modifier.height(48.dp), verticalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = text,
            )
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFff9c33)
private fun LabelAssignmentView(
    modifier: Modifier = Modifier,
    label: String = "Category",
    text: String = "Drama",
    backgroundColor: Color = Color.White,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            text = label,
        )
        Spacer(modifier = Modifier.width(24.dp))
        ClippedTextField(text = text, backgroundColor = backgroundColor)
    }
}