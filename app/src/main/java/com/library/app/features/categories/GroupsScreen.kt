package com.library.app.features.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.library.app.features.main.MAX_ITEMS_IN_ROW
import com.library.presentation.composables.books.AddBookView
import com.library.presentation.composables.containers.ScaffoldUI
import com.library.presentation.composables.containers.Screen


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel,
    windowSizeClass: WindowSizeClass,
) {
    val groups by remember { viewModel.groups }
    Screen(
        viewModel = viewModel,
        scaffoldUI = ScaffoldUI(
            isNeedToShowNavigationBar = true,
            content = {
                LazyColumn {
                    item {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(horizontal = 16.dp), maxItemsInEachRow = MAX_ITEMS_IN_ROW,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            AddBookView(
                                onAddBookClick = {

                                })
                        }
                    }
                }
            }
        )
    )
}