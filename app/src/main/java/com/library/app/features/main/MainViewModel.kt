package com.library.app.features.main

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.library.data.models.books.BookUi
import com.library.presentation.BaseViewModel
import com.library.presentation.navigation.route.RouteNavigator
import com.library.providers.api.sevices.data.LibraryRepository
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

    private val capturedImageUri: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)

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
                                libraryRepository.getBookMetadata(barcode)
                                Log.d("Barcode", barcode)
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
}