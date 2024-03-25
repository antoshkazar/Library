package com.library.app.features.books

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.library.R
import com.library.presentation.composables.containers.ScaffoldUI
import com.library.presentation.composables.containers.Screen
import com.library.presentation.theme.Brown60
import com.library.presentation.theme.Brown80
import com.library.presentation.theme.LibraryTypography

@Composable
fun BookScreen(
    viewModel: BookViewModel,
    windowSizeClass: WindowSizeClass,
) {
    val currentBookUi by remember {
        viewModel.currentBookUi
    }
    val username by remember {
        viewModel.username
    }

    Screen(
        viewModel = viewModel,
        onScreenLaunch = viewModel::onScreenLaunch,
        scaffoldUI = ScaffoldUI(
            isNeedToShowNavigationBar = true,
            content = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brown80)
                ) {
                    item {
                        Text(
                            text = currentBookUi.title,
                            style = LibraryTypography.titleLarge,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 24.dp, top = 16.dp)
                        )

                        Divider(
                            modifier = Modifier.padding(vertical = 24.dp),
                            thickness = 2.dp,
                            color = Color.White
                        )

                        Text(
                            text = stringResource(id = R.string.main_info),
                            style = LibraryTypography.titleSmall,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 24.dp)
                        )

                        BookPropertiesContainer(
                            backgroundColor = Brown60,
                            textAndLabels = listOf(
                                TextAndSubtextUi(
                                    text = currentBookUi.authors,
                                    subtext = "Authors",
                                ),
                                TextAndSubtextUi(
                                    text = currentBookUi.publisher,
                                    subtext = "publisher",
                                ),
                                TextAndSubtextUi(
                                    text = currentBookUi.isbn,
                                    subtext = "ISBN",
                                ),
                            )
                        )

                        Text(
                            text = stringResource(id = R.string.additional_information),
                            style = LibraryTypography.titleSmall,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 24.dp)
                        )

                        BookPropertiesContainer(
                            backgroundColor = Brown60,
                            textAndLabels = listOf(
                                TextAndSubtextUi(
                                    text = currentBookUi.language,
                                    subtext = "language",
                                ),
                                TextAndSubtextUi(
                                    text = currentBookUi.year,
                                    subtext = "year",
                                ),
                            )
                        )
                    }
                }
            }
        )
    )
}

@Composable
@Preview
private fun TextAndSubtextView(
    modifier: Modifier = Modifier,
    uiData: TextAndSubtextUi = TextAndSubtextUi(),
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
) {

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .background(backgroundColor), verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = uiData.text,
            style = LibraryTypography.titleMedium,
            color = textColor,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(uiData.iconResId),
                contentDescription = "clear text",
            )
            Text(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = uiData.subtext,
                style = LibraryTypography.bodySmall,
                color = textColor,
            )
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFff9c33)
private fun BookPropertiesContainer(
    modifier: Modifier = Modifier,
    textAndLabels: List<TextAndSubtextUi> = emptyList<TextAndSubtextUi>(),
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(24.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        textAndLabels.forEach {
            if (it.text.isNotEmpty()) {
                TextAndSubtextView(
                    uiData = it,
                    backgroundColor = backgroundColor,
                    textColor = textColor,
                )
                Divider(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.White
                )
            }
        }
    }
}

private data class TextAndSubtextUi(
    val text: String = "174389013498u8190",
    val subtext: String = "ISBN",
    val iconResId: Int = R.drawable.ic_open_book,
)

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
        //TextAndSubtextView(text = text, backgroundColor = backgroundColor)
    }
}