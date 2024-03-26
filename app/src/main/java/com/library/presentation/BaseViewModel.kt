package com.library.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.library.presentation.composables.LoaderWidgetState

abstract class BaseViewModel : ViewModel() {
    val progressState = mutableStateOf(LoaderWidgetState(isLoading = false))

    open fun navigateToCategories() {}

    open fun navigateToBooks() {}
}