package com.library.app.features.main

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.library.app.navigation.route.BookRoute
import com.library.app.navigation.route.GroupRoute
import com.library.app.navigation.route.ScanQrRoute
import com.library.core.extensions.addToList
import com.library.core.extensions.doIfSuccess
import com.library.core.extensions.replaceAllInList
import com.library.data.models.books.BookModel
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
        emptyList<BookModel>()
    )
    val searchText = mutableStateOf("")
    val isSearching = mutableStateOf(false)
    val searchHistory = mutableStateOf(listOf("wados", "ajfdb"))

    private val capturedImageUri: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)

    fun onScreenLaunch() = viewModelScope.launch {
        val lastQr = libraryRepository.getScannedQr()
        if (lastQr.isNotEmpty()) {
            onQrReceived(lastQr)
        } else {
            getUserBooks()
        }
    }

    private fun onQrReceived(barcode: String) {
        if (!books.value.any { it.metadata.isbn == barcode }) {
            showQuestionDialog.value = true
        } else {
            books.value.firstOrNull { it.metadata.isbn == barcode }?.let {
                onBookClick(it)
            }
            viewModelScope.launch { libraryRepository.updateScannedQr("") }
        }
    }

    fun onCloseWarningDialog() {
        showQuestionDialog.value = false
        viewModelScope.launch { libraryRepository.updateScannedQr("") }
    }

    fun onConfirmAddBook() = viewModelScope.launch {
        showQuestionDialog.value = false
        val qr = libraryRepository.getScannedQr()
        addBook(qr)
        libraryRepository.updateScannedQr("")
    }

    private suspend fun getUserBooks() {
        books.value = emptyList()
        libraryRepository.getUserBooks(authPreference.identifier).convertToDataState()
            .doIfSuccess {
                books.value = books.value.replaceAllInList(it)
            }
    }

    override fun navigateToCategories() {
        navigateToRoute(GroupRoute.routeWithParams(authPreference.rootGroupId)) //todo
    }

    fun onUriReceived(uri: Uri) = viewModelScope.launch {
        capturedImageUri.emit(uri)
        processImage(uri)
    }

    private fun addBook(barcode: String) = viewModelScope.launch {
        libraryRepository.addBook(barcode, authPreference.rootGroupId)
            .convertToDataState()
            .doIfSuccess {
                books.value = books.value.addToList(it)
            }
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
                            addBook(barcode)
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

    fun onScanQrClick() {
        routeNavigator.navigateToRoute(ScanQrRoute.route)
    }

    fun onToggleSearch() {
        isSearching.value = isSearching.value.not()
        if (!isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun onBookClick(bookUi: BookModel) {
        navigateToRoute(BookRoute.routeWithParams(bookUi = bookUi))
    }
}