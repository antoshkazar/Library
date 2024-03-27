package com.library.app.features.books

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.library.R
import com.library.presentation.composables.TopBar
import com.library.presentation.composables.TopBarUI
import com.library.presentation.composables.containers.ScaffoldUI
import com.library.presentation.composables.containers.Screen
import com.library.presentation.theme.Brown30
import com.library.presentation.theme.Brown60
import com.library.presentation.theme.LibraryTypography

@OptIn(ExperimentalMaterial3Api::class)
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

    var expanded by remember {
        mutableStateOf(false)
    }

    val allGroups by remember {
        viewModel.allGroups
    }

    var selectedText by remember { viewModel.selectedText }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.KeyboardArrowDown

    val context = LocalContext.current

    Screen(
        viewModel = viewModel,
        onScreenLaunch = viewModel::onScreenLaunch,
        scaffoldUI = ScaffoldUI(
            isNeedToShowNavigationBar = false,
            topBar = {
                TopBar(
                    uiData = TopBarUI(
                        title = currentBookUi.metadata.title,
                        hasBackButton = true,
                    ),
                    windowSizeClass = windowSizeClass,
                    onClickBackButton = viewModel::onClickBackButton,
                )
            },
            content = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            OutlinedTextField(
                                readOnly = true,
                                value = selectedText,
                                onValueChange = { },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        textfieldSize = coordinates.size.toSize()
                                    }
                                    .padding(horizontal = 24.dp),
                                label = { Text("Book group") },
                                trailingIcon = {
                                    Icon(icon, "contentDescription",
                                        Modifier.clickable { expanded = !expanded })
                                },
                                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Brown30)
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .width(
                                        with(LocalDensity.current, { textfieldSize.width.toDp() })
                                    )
                                    .padding(horizontal = 24.dp)
                            ) {
                                allGroups.forEach { group ->
                                    DropdownMenuItem(text = {
                                        Text(text = group.name)
                                    }, onClick = {
                                        expanded = false
                                        selectedText = group.name
                                        viewModel.moveBook(group)
                                        Toast.makeText(
                                            context,
                                            "Group changed!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    })
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
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
                                    text = currentBookUi.metadata.authors,
                                    subtext = "Authors",
                                ),
                                TextAndSubtextUi(
                                    text = currentBookUi.metadata.publisher,
                                    subtext = "publisher",
                                ),
                                TextAndSubtextUi(
                                    text = currentBookUi.metadata.isbn,
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
                                    text = currentBookUi.metadata.language,
                                    subtext = "language",
                                ),
                                TextAndSubtextUi(
                                    text = currentBookUi.metadata.year,
                                    subtext = "year",
                                ),
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))
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
        textAndLabels.forEachIndexed { index, item ->
            if (item.text.isNotEmpty()) {
                TextAndSubtextView(
                    uiData = item,
                    backgroundColor = backgroundColor,
                    textColor = textColor,
                )
                if (index != textAndLabels.lastIndex) {
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
        Spacer(modifier = Modifier.height(8.dp))
    }
}

private data class TextAndSubtextUi(
    val text: String = "174389013498u8190",
    val subtext: String = "ISBN",
    val iconResId: Int = R.drawable.ic_open_book,
)