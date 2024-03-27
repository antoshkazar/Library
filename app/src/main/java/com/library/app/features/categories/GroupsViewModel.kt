package com.library.app.features.categories

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.library.app.navigation.route.BookRoute
import com.library.app.navigation.route.GroupRoute
import com.library.app.navigation.route.GroupRoute.KEY_GROUP
import com.library.app.navigation.route.MainRoute
import com.library.core.extensions.addToList
import com.library.core.extensions.doIfFailure
import com.library.core.extensions.doIfSuccess
import com.library.core.extensions.fromJson
import com.library.data.models.books.BookUi
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
class GroupsViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val libraryRepository: LibraryRepository,
    private val fileProvider: FileProvider,
    private val authPreference: AuthPreference,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(), RouteNavigator by routeNavigator {
    val currentGroup: MutableState<Group> = mutableStateOf(Group())
    val subgroups: MutableState<List<Group>> = mutableStateOf(emptyList())
    val subBooks: MutableState<List<BookUi>> = mutableStateOf(emptyList())
    val newGroupName = mutableStateOf("")

    init {
        savedStateHandle.get<String>(KEY_GROUP)
            ?.let {
                val routeParam = Uri.decode(it).fromJson(GroupRoute.Param::class.java)
                currentGroup.value = Group(identifier = routeParam.groupId)
            }
    }

    fun onScreenLaunch() = viewModelScope.launch {
        libraryRepository.getGroup(currentGroup.value.identifier).convertToDataState().doIfSuccess {
            currentGroup.value = it
        }
        currentGroup.value.subgroupsIds.forEach { subgroup ->
            libraryRepository.getGroup(subgroup.toString()).convertToDataState().doIfSuccess {
                if (!subgroups.value.contains(it))
                    subgroups.value = subgroups.value.addToList(it)
            }
        }

        currentGroup.value.booksIds.forEach { subgroup ->
            libraryRepository.getBook(subgroup.toString()).convertToDataState().doIfSuccess {
                if (!subBooks.value.contains(it.metadata))
                    subBooks.value = subBooks.value.addToList(it.metadata)
            }
        }
    }

    fun onGroupNameChange(value: String) {
        newGroupName.value = value
    }

    override fun navigateToCategories() {
        navigateToRoute(GroupRoute.routeWithParams(currentGroup.value.identifier))
    }

    override fun navigateToBooks() {
        popToRoute(MainRoute.route)
    }

    fun removeItem(group: Group) {
        val mutableList = subgroups.value.toMutableList()
        mutableList.remove(group)
        subgroups.value = mutableList
    }

    fun onBookClick(bookUi: BookUi) {
        navigateToRoute(BookRoute.routeWithParams(bookUi = bookUi))
    }

    fun onCreateGroupClick() {
        viewModelScope.launch {
            libraryRepository.addGroup(
                name = newGroupName.value,
                parentGroupId = currentGroup.value.identifier,
            ).convertToDataState()
                .doIfFailure {
                    Log.d("Failure:", it)
                }
        }
    }
}