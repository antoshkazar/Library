package com.library.app.features.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.library.R
import com.library.data.models.screen.Screens
import com.library.presentation.composables.EmptyView
import com.library.presentation.composables.EmptyViewUI
import com.library.presentation.composables.TopBar
import com.library.presentation.composables.TopBarUI
import com.library.presentation.composables.books.BookView
import com.library.presentation.composables.containers.GroupItem
import com.library.presentation.composables.containers.ScaffoldUI
import com.library.presentation.composables.containers.Screen
import com.library.presentation.composables.input.InputTextField
import com.library.presentation.composables.input.InputTextFieldUI
import com.library.presentation.theme.LibraryTypography
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel,
    windowSizeClass: WindowSizeClass,
) {
    val currentGroup by remember { viewModel.currentGroup }
    val subgroups by remember {
        viewModel.subgroups
    }
    val subBooks by remember {
        viewModel.subBooks
    }

    val newGroupName by remember {
        viewModel.newGroupName
    }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showRegistrationBottomSheet by remember { mutableStateOf(false) }

    val isBackButtonVisible by remember {
        viewModel.isBackButtonVisible
    }
    Screen(
        viewModel = viewModel,
        onScreenLaunch = viewModel::onScreenLaunch,
        screenType = Screens.GROUP,
        scaffoldUI = ScaffoldUI(
            isNeedToShowNavigationBar = true,
            topBar = {
                TopBar(
                    uiData = TopBarUI(
                        title = currentGroup.name,
                        hasBackButton = isBackButtonVisible,
                    ),
                    windowSizeClass = windowSizeClass,
                    onClickBackButton = viewModel::onBackButtonClick
                )
            },
            content = {
                Box {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(modifier = Modifier.fillMaxWidth())
                            {
                                Text(
                                    text = stringResource(id = R.string.subgroups),
                                    style = LibraryTypography.titleSmall,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            if (subgroups.isNotEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(horizontal = 16.dp),
                                ) {
                                    subgroups.forEach { groupContent ->
                                        GroupItem(
                                            groupContent,
                                            onRemove = viewModel::onDeleteItemClick,
                                            onGroupClick = viewModel::onGroupClick
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.height(16.dp))
                                EmptyView(
                                    uiData = EmptyViewUI(
                                        text = stringResource(id = R.string.nothing_found),
                                        hasTopSpacer = false
                                    ),
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = stringResource(id = R.string.group_books),
                                    style = LibraryTypography.titleSmall,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            if (subBooks.isNotEmpty()) {
                                FlowRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    subBooks.forEach { bookUi ->
                                        BookView(
                                            bookUi = bookUi,
                                            modifier = Modifier.clickable {
                                                viewModel.onBookClick(
                                                    bookUi
                                                )
                                            })
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.height(16.dp))
                                EmptyView(
                                    uiData = EmptyViewUI(
                                        text = stringResource(id = R.string.nothing_found),
                                        hasTopSpacer = false
                                    ),
                                )
                            }
                        }
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = 16.dp),
                        onClick = { showRegistrationBottomSheet = true },
                    ) {
                        Icon(Icons.Filled.Add, "Floating action button.")
                    }
                }

                if (showRegistrationBottomSheet) {
                    ModalBottomSheet(
                        modifier = Modifier,
                        onDismissRequest = {
                            showRegistrationBottomSheet = false
                        },
                        sheetState = sheetState
                    ) {

                        InputTextField(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            uiData = InputTextFieldUI(
                                text = newGroupName,
                                label = stringResource(id = R.string.subgroup_name)
                            ),
                            onValueChange = viewModel::onGroupNameChange,
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            enabled = newGroupName.isNotEmpty(),
                            onClick = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showRegistrationBottomSheet = false
                                    }
                                }
                                viewModel.onCreateGroupClick()
                            }) {
                            Text(text = stringResource(id = R.string.create))
                        }
                    }
                }
            }
        )
    )
}