package com.library.app.features.main

import androidx.compose.runtime.mutableStateOf
import com.library.data.models.books.BookUi
import com.library.presentation.BaseViewModel
import com.library.presentation.navigation.route.RouteNavigator
import com.library.providers.api.sevices.data.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val libraryRepository: LibraryRepository,
) : BaseViewModel(), RouteNavigator by routeNavigator {
    val books = mutableStateOf(
        listOf(
            BookUi(title = "3 Мушкетера"),
            BookUi(title = "Война и мир"),
            BookUi(title = "Анна Каренина"),
            BookUi(title = "Бесы"),
            BookUi(title = "Мартышка и очки")
        )
    )
    val searchText = mutableStateOf("")
    val isSearching = mutableStateOf(false)
    val searchHistory = mutableStateOf(listOf("wados", "ajfdb"))

    fun onSearchTextChange(text: String) {
        searchText.value = text
    }

    fun onAddBookClick() {

    }

    fun onToggleSearch() {
        isSearching.value = isSearching.value.not()
        if (!isSearching.value) {
            onSearchTextChange("")
        }
    }
}