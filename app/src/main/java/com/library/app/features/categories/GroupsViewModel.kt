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
import com.library.core.extensions.doIfSuccessSuspend
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
class GroupsViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val libraryRepository: LibraryRepository,
    private val fileProvider: FileProvider,
    private val authPreference: AuthPreference,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(), RouteNavigator by routeNavigator {
    val currentGroup: MutableState<Group> = mutableStateOf(Group())
    val subgroups: MutableState<List<Group>> = mutableStateOf(emptyList())
    val subBooks: MutableState<List<BookModel>> = mutableStateOf(emptyList())
    val newGroupName = mutableStateOf("")
    val isBackButtonVisible = mutableStateOf(false)

    init {
        savedStateHandle.get<String>(KEY_GROUP)
            ?.let {
                val routeParam = Uri.decode(it).fromJson(GroupRoute.Param::class.java)
                currentGroup.value = Group(groupIdentifier = routeParam.groupId)
            }
    }

    fun onScreenLaunch() {
        isBackButtonVisible.value = currentGroup.value.groupIdentifier != authPreference.rootGroupId
        viewModelScope.launch {
            getGroup()
            getSubgroups()
            getSubBooks()
        }
    }

    private suspend fun getGroup() {
        libraryRepository.getGroup(currentGroup.value.groupIdentifier).convertToDataState()
            .doIfSuccess {
                currentGroup.value = it
            }
    }

    private suspend fun getSubgroups() {
        subgroups.value = emptyList()
        currentGroup.value.subgroupsIds.forEach { subgroup ->
            libraryRepository.getGroup(subgroup.toString()).convertToDataState().doIfSuccess {
                subgroups.value = subgroups.value.addToList(it)
            }
        }
    }

    private suspend fun getSubBooks() {
        currentGroup.value.booksIds.forEach { book ->
            libraryRepository.getBook(book.toString()).convertToDataState().doIfSuccess { newBook ->
                if (!subBooks.value.any { book -> book.metadata.isbn == newBook.metadata.isbn })
                    subBooks.value = subBooks.value.addToList(newBook)
            }
        }
    }

    fun onGroupNameChange(value: String) {
        newGroupName.value = value
    }

    override fun navigateToCategories() {
        navigateToRoute(GroupRoute.routeWithParams(currentGroup.value.groupIdentifier))
    }

    fun onBackButtonClick() {
        navigateUp()
    }

    override fun navigateToBooks() {
        popToRoute(MainRoute.route)
    }

    fun onDeleteItemClick(group: Group) = viewModelScope.launch {
        libraryRepository.deleteGroup(
            groupId = group.groupIdentifier,
            parentGroupId = currentGroup.value.groupIdentifier
        ).convertToDataState().doIfSuccess {
            if (it.toBooleanStrictOrNull() == true) {
                val mutableList = subgroups.value.toMutableList()
                mutableList.remove(group)
                subgroups.value = mutableList
            }
        }
    }

    fun onGroupClick(group: Group) {
        navigateToRoute(GroupRoute.routeWithParams(group.groupIdentifier))
    }

    fun onBookClick(bookUi: BookModel) {
        navigateToRoute(BookRoute.routeWithParams(bookUi = bookUi))
    }

    fun onCreateGroupClick() {
        viewModelScope.launch {
            libraryRepository.addGroup(
                name = newGroupName.value,
                parentGroupId = currentGroup.value.groupIdentifier,
            ).convertToDataState().doIfSuccessSuspend {
                getGroup()
                getSubgroups()
            }.doIfFailure {
                Log.d("Failure:", it)
            }
        }
    }

    private fun List<Group>.containsGroup(group: Group): Boolean =
        this.any { it.groupIdentifier == group.groupIdentifier }
}