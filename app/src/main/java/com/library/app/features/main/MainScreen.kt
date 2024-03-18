package com.library.app.features.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.library.presentation.composables.containers.BOOK_VIEW_SIZE
import com.library.presentation.composables.containers.BookView
import com.library.presentation.composables.containers.ScaffoldUI
import com.library.presentation.composables.containers.Screen

const val MAX_ITEMS_IN_ROW = 2

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    windowSizeClass: WindowSizeClass,
) {
    val books by remember { viewModel.books }
    val searchText by remember { viewModel.searchText }
    val isSearching by remember {
        viewModel.isSearching
    }

    Screen(
        viewModel = viewModel,
        scaffoldUI = ScaffoldUI(
            topBar = {
                SearchBar(
                    query = searchText,//text showed on SearchBar
                    onQueryChange = viewModel::onSearchTextChange, //update the value of searchText
                    onSearch = viewModel::onSearchTextChange, //the callback to be invoked when the input service triggers the ImeAction.Search action
                    active = isSearching, //whether the user is searching or not
                    onActiveChange = { viewModel.onToggleSearch() }, //the callback to be invoked when this search bar's active state is changed
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    content = {},
                )
            },
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
                            books.forEach { bookUi ->
                                BookView(bookUi = bookUi)
                            }
                            Box(modifier = Modifier.size(BOOK_VIEW_SIZE.dp))
                        }
                    }
                }
            }
        )
    )
}