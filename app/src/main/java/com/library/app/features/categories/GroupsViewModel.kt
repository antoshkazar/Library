package com.library.app.features.categories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.library.data.models.groups.Group
import com.library.presentation.BaseViewModel
import com.library.presentation.navigation.route.RouteNavigator
import com.library.providers.api.sevices.data.LibraryRepository
import com.library.providers.fileProvider.FileProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val libraryRepository: LibraryRepository,
    private val fileProvider: FileProvider,
) : BaseViewModel(), RouteNavigator by routeNavigator {
    val groups: MutableState<Group> = mutableStateOf(Group())
}