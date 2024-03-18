package com.library.app.features.camera

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable

open class CameraViewModel(
    private val setScreenStateRegular: () -> Unit = {},
    private val setScreenStateEmptyView: () -> Unit = {},
    viewModelState: SavedStateHandle,
) : ViewModel() {

    val shouldCheckCameraPermissionState = mutableStateOf(false)
    val shouldRequestCameraPermissionState = mutableStateOf(false)
    val shouldOpenCameraAppState = mutableStateOf(false)
    val isCameraPermissionAlreadyRequested = mutableStateOf(false)
    val shouldOpenAppSettingsState = mutableStateOf(false)

    @OptIn(SavedStateHandleSaveableApi::class)
    var uriForTakePhotoState: Uri by viewModelState.saveable {
        mutableStateOf(Uri.EMPTY)
    }
    private val isCameraPermissionRationale = mutableStateOf(false)


    fun checkCameraPermission() {
        shouldCheckCameraPermissionState.value = true
    }


    fun requestCameraPermission() {
        shouldRequestCameraPermissionState.value = true
    }

    fun setCheckCameraPermissionFalse() {
        shouldCheckCameraPermissionState.value = false
    }

    fun setRequestCameraPermissionFalse() {
        shouldRequestCameraPermissionState.value = false
    }

    fun setOpenCameraAppFalse() {
        shouldOpenCameraAppState.value = false
    }

    fun setShouldOpenAppSettingsFalse() {
        shouldOpenAppSettingsState.value = false
    }

    fun onCameraPermissionShouldRationale() {
        setScreenStateEmptyView()
        setRequestCameraPermissionFalse()
        isCameraPermissionRationale.value = true
    }

    open fun onCameraPermissionGranted(fileProvider: FileProvider? = null) {
        setScreenStateRegular()
       // uriForTakePhotoState = fileProvider?.createPhotoFile() ?: Uri.EMPTY
        shouldOpenCameraAppState.value = true
    }

    open fun onTakeCameraPermissionResult(isGranted: Boolean, fileProvider: FileProvider? = null) {
        if (isGranted) setScreenStateRegular() else setScreenStateEmptyView()

        if (isGranted) {
            onCameraPermissionGranted(fileProvider)
        } else {
            isCameraPermissionAlreadyRequested.value = true
            checkCameraPermission()
        }
    }
}