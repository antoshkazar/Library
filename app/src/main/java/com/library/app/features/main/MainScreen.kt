package com.library.app.features.main

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.library.BuildConfig
import com.library.R
import com.library.core.extensions.createImageFile
import com.library.data.models.screen.Screens
import com.library.presentation.composables.EmptyView
import com.library.presentation.composables.EmptyViewUI
import com.library.presentation.composables.books.AddBookView
import com.library.presentation.composables.books.BookView
import com.library.presentation.composables.containers.ScaffoldUI
import com.library.presentation.composables.containers.Screen
import java.util.Objects

//const val MAX_ITEMS_IN_ROW = 2

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
    val searchHistory by remember { viewModel.searchHistory }

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )


    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            viewModel.onUriReceived(uri)
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val onAddBookClick = {
        val permissionCheckResult =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            )
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            cameraLauncher.launch(uri)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Screen(
        viewModel = viewModel,
        onScreenLaunch = viewModel::onScreenLaunch,
        screenType = Screens.BOOKS,
        scaffoldUI = ScaffoldUI(
            topBar = {
                SearchBar(
                    query = searchText,
                    onQueryChange = viewModel::onSearchTextChange,
                    onSearch = viewModel::onSearchTextChange,
                    active = isSearching,
                    onActiveChange = { viewModel.onToggleSearch() },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "search",
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    content = {
                        LazyColumn {
                            searchHistory.forEach { searchText ->
                                item {
                                    Text(
                                        text = searchText,
                                        modifier = Modifier.padding(
                                            start = 8.dp,
                                            top = 4.dp,
                                            end = 8.dp,
                                            bottom = 4.dp
                                        )
                                    )
                                }
                            }
                        }
                    },
                )
            },
            isNeedToShowNavigationBar = true,
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (books.isNotEmpty()) {
                        LazyColumn {
                            item {
                                FlowRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    // maxItemsInEachRow = MAX_ITEMS_IN_ROW,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    books.forEach { bookUi ->
                                        BookView(
                                            bookUi = bookUi.metadata,
                                            modifier = Modifier.clickable {
                                                viewModel.onBookClick(
                                                    bookUi
                                                )
                                            })
                                    }
                                    AddBookView(
                                        onAddBookClick = onAddBookClick
                                    )
                                }
                            }
                        }
                    } else {
                        EmptyView(
                            uiData = EmptyViewUI(
                                text = stringResource(id = R.string.nothing_found),
                            ),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = 16.dp),
                        onClick = onAddBookClick,
                    ) {
                        Icon(Icons.Filled.Add, "Floating action button.")
                    }
                }
            }
        )
    )
}