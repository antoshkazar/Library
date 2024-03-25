package com.library.app.features.books

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.library.app.navigation.route.BookRoute
import com.library.core.extensions.fromJson
import com.library.data.models.books.BookUi
import com.library.presentation.BaseViewModel
import com.library.presentation.navigation.route.RouteNavigator
import com.library.providers.api.sevices.data.LibraryRepository
import com.library.providers.auth.AuthPreference
import com.library.providers.fileProvider.FileProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val libraryRepository: LibraryRepository,
    private val fileProvider: FileProvider,
    private val authPreference: AuthPreference,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(), RouteNavigator by routeNavigator {
    val currentBookUi = mutableStateOf(BookUi())
    val username = mutableStateOf("")

    init {
        savedStateHandle.get<String>(BookRoute.KEY_BOOK)
            ?.let {
                val routeParam = Uri.decode(it).fromJson(BookRoute.Param::class.java)
                currentBookUi.value = routeParam.bookUi
            }
    }

    fun onScreenLaunch() {
        username.value = authPreference.name
    }
}