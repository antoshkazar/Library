package com.library.app.features.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.library.presentation.composables.books.AddBookView
import com.library.presentation.composables.books.BookView
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
    val searchHistory by remember { viewModel.searchHistory }

//    CameraHandler(
//        shouldRequestCameraPermission = viewModel.cameraViewModel.shouldRequestCameraPermissionState.value,
//        shouldCheckCameraPermission = viewModel.cameraViewModel.shouldCheckCameraPermissionState.value,
//        shouldOpenCameraApp = viewModel.cameraViewModel.shouldOpenCameraAppState.value,
//        isCameraPermissionAlreadyRequested = viewModel.cameraViewModel.isCameraPermissionAlreadyRequested.value,
//        shouldOpenAppSettings = viewModel.cameraViewModel.shouldOpenAppSettingsState.value,
//        uriForTakePhoto = viewModel.cameraViewModel.uriForTakePhotoState,
//        onCameraPermissionGranted = viewModel::onCameraPermissionGranted,
//        onTakeCameraPermissionResult = viewModel::onTakeCameraPermissionResult,
//        onTakePhotoResult = viewModel::onTakePhotoResult,
//        requestCameraPermission = viewModel.cameraViewModel::requestCameraPermission,
//        setRequestCameraPermissionFalse = viewModel.cameraViewModel::setRequestCameraPermissionFalse,
//        setCheckCameraPermissionFalse = viewModel.cameraViewModel::setCheckCameraPermissionFalse,
//        setOpenCameraAppFalse = viewModel.cameraViewModel::setOpenCameraAppFalse,
//        onCameraPermissionPermanentDeny = viewModel::onCameraPermissionPermanentDeny,
//        onCameraPermissionShouldRationale = viewModel.cameraViewModel::onCameraPermissionShouldRationale,
//        setShouldOpenAppSettingsFalse = viewModel.cameraViewModel::setShouldOpenAppSettingsFalse,
//    )

    Screen(
        viewModel = viewModel,
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
                            AddBookView(onAddBookClick = viewModel::onAddBookClick)
                        }
                    }
                }
            }
        )
    )
}