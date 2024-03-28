package com.library.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.library.presentation.composables.LoaderWidgetState
import com.library.presentation.composables.QuestionDialogUI

abstract class BaseViewModel : ViewModel() {
    val progressState = mutableStateOf(LoaderWidgetState(isLoading = false))
    val showQuestionDialog: MutableState<Boolean> = mutableStateOf(false)
    val questionData: MutableState<QuestionDialogUI> = mutableStateOf(QuestionDialogUI())
    open fun navigateToCategories() {}

    open fun navigateToBooks() {}
}