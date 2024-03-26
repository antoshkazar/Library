package com.library.app.features.main

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.library.app.navigation.route.BookRoute
import com.library.app.navigation.route.GroupRoute
import com.library.core.extensions.addAllToList
import com.library.core.extensions.addToList
import com.library.core.extensions.doIfSuccess
import com.library.data.models.books.BookUi
import com.library.presentation.BaseViewModel
import com.library.presentation.navigation.route.RouteNavigator
import com.library.providers.api.handlers.convertToDataState
import com.library.providers.api.sevices.data.LibraryRepository
import com.library.providers.auth.AuthPreference
import com.library.providers.fileProvider.FileProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val libraryRepository: LibraryRepository,
    private val fileProvider: FileProvider,
    private val authPreference: AuthPreference,
) : BaseViewModel(), RouteNavigator by routeNavigator {
    val books = mutableStateOf(
        emptyList<BookUi>()
    )
    val searchText = mutableStateOf("")
    val isSearching = mutableStateOf(false)
    val searchHistory = mutableStateOf(listOf("wados", "ajfdb"))

    private val capturedImageUri: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)

    fun onScreenLaunch() = viewModelScope.launch {
        libraryRepository.getUserBooks(authPreference.identifier).convertToDataState()
            .doIfSuccess {
                books.value = books.value.addAllToList(it.map { model -> model.metadata })
            }
    }

    override fun navigateToCategories() {
        navigateToRoute(GroupRoute.routeWithParams("1"))//authPreference.rootGroupId)) //todo
    }

    fun onUriReceived(uri: Uri) = viewModelScope.launch {
        capturedImageUri.emit(uri)
        processImage(uri)
    }

    private fun processImage(uri: Uri) {
        try {
            val image =
                fileProvider.getInputImageFromUri(uri)
            val scanner = BarcodeScanning.getClient()
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        barcodes.first().rawValue?.let { barcode ->
                            viewModelScope.launch {
                                libraryRepository.addBook(barcode, authPreference.rootGroupId)
                                    .convertToDataState()
                                    .doIfSuccess {
                                        books.value = books.value.addToList(it.metadata)
                                    }
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Error", e.stackTraceToString())
                }
        } catch (e: IOException) {
            Log.e("Error", e.stackTraceToString())
        }
    }

    fun onSearchTextChange(text: String) {
        searchText.value = text
    }

    fun onToggleSearch() {
        isSearching.value = isSearching.value.not()
        if (!isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun onBookClick(bookUi: BookUi) {
        navigateToRoute(BookRoute.routeWithParams(bookUi = bookUi))
    }
}