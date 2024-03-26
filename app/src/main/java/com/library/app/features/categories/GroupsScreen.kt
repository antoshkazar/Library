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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.library.R
import com.library.data.models.screen.Screens
import com.library.presentation.composables.TopBar
import com.library.presentation.composables.TopBarUI
import com.library.presentation.composables.books.BookView
import com.library.presentation.composables.containers.GroupItem
import com.library.presentation.composables.containers.ScaffoldUI
import com.library.presentation.composables.containers.Screen
import com.library.presentation.theme.LibraryTypography


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
                        hasBackButton = false,
                    ),
                    windowSizeClass = windowSizeClass,
                )
            },
            content = {
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
                                    .padding(start = 24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(horizontal = 16.dp),
                        ) {
                            subgroups.forEach { groupContent ->
                                GroupItem(groupContent, onRemove = viewModel::removeItem)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = stringResource(id = R.string.group_books),
                                style = LibraryTypography.titleSmall,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(start = 24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            subBooks.forEach { bookUi ->
                                BookView(
                                    bookUi = bookUi,
                                    modifier = Modifier.clickable { viewModel.onBookClick(bookUi) })
                            }
                        }
                    }
                }
            }
        )
    )
}