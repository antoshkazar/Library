package com.library.app.features.books

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.library.app.navigation.route.BookRoute
import com.library.core.extensions.doIfSuccess
import com.library.core.extensions.fromJson
import com.library.data.models.books.BookModel
import com.library.data.models.groups.Group
import com.library.presentation.BaseViewModel
import com.library.presentation.navigation.route.RouteNavigator
import com.library.providers.api.handlers.convertToDataState
import com.library.providers.api.sevices.data.LibraryRepository
import com.library.providers.auth.AuthPreference
import com.library.providers.fileProvider.FileProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val libraryRepository: LibraryRepository,
    private val fileProvider: FileProvider,
    private val authPreference: AuthPreference,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(), RouteNavigator by routeNavigator {
    val currentBookUi = mutableStateOf(BookModel())
    val username = mutableStateOf("")
    val isCategoryExpanded = mutableStateOf(false)
    val allGroups = mutableStateOf(emptyList<Group>())
    val selectedText = mutableStateOf("")
    val currentGroup: MutableState<Group?> = mutableStateOf(null)

    init {
        savedStateHandle.get<String>(BookRoute.KEY_BOOK)
            ?.let {
                val routeParam = Uri.decode(it).fromJson(BookRoute.Param::class.java)
                currentBookUi.value = routeParam.bookUi
            }
    }

    fun moveBook(moveTo: Group) = viewModelScope.launch {
        libraryRepository.moveBook(
            bookId = currentBookUi.value.identifier,
            currentGroupId = currentGroup.value?.groupIdentifier ?: authPreference.rootGroupId,
            targetGroupId = moveTo.groupIdentifier
        )
    }

    fun onScreenLaunch() {
        username.value = authPreference.name
        viewModelScope.launch {
            libraryRepository.getUserGroups(userId = authPreference.identifier).convertToDataState()
                .doIfSuccess {
                    allGroups.value = it
                    if (it.isNotEmpty()) {
                        currentGroup.value =
                            it.firstOrNull { group -> group.booksIds.contains(currentBookUi.value.identifier.toInt()) }
                        selectedText.value = currentGroup.value?.name.orEmpty()
                    }
                }
        }
    }

    fun onClickBackButton() {
        navigateUp()
    }
}