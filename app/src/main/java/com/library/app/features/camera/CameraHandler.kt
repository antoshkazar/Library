package com.library.app.features.camera

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraHandler(
    shouldRequestCameraPermission: Boolean = false,
    shouldCheckCameraPermission: Boolean = false,
    shouldOpenCameraApp: Boolean = false,
    isCameraPermissionAlreadyRequested: Boolean = false,
    shouldOpenAppSettings: Boolean = false,
    uriForTakePhoto: Uri = Uri.EMPTY,
    onCameraPermissionGranted: () -> Unit = {},
    onTakeCameraPermissionResult: (Boolean) -> Unit = {},
    onTakePhotoResult: (Boolean) -> Unit = {},
    requestCameraPermission: () -> Unit = {},
    setRequestCameraPermissionFalse: () -> Unit = {},
    setCheckCameraPermissionFalse: () -> Unit = {},
    setOpenCameraAppFalse: () -> Unit = {},
    onCameraPermissionShouldRationale: () -> Unit = {},
    onCameraPermissionPermanentDeny: () -> Unit = { },
    setShouldOpenAppSettingsFalse: () -> Unit = {}
) {

    val cameraPermissionState =
        rememberPermissionState(Manifest.permission.CAMERA) { isGranted ->
            onTakeCameraPermissionResult(isGranted)
        }
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        onTakePhotoResult(isSuccess)
    }

    if (shouldCheckCameraPermission) {
        setCheckCameraPermissionFalse()

        with(cameraPermissionState.status) {
            if (isGranted) {
                onCameraPermissionGranted()
            } else {
                when {
                    shouldShowRationale -> {
                        onCameraPermissionShouldRationale()
                    }

                    isCameraPermissionAlreadyRequested && !shouldShowRationale -> {
                        onCameraPermissionPermanentDeny()
                    }

                    else -> requestCameraPermission()
                }
            }
        }

    }

    LaunchedEffect(shouldRequestCameraPermission) {
        if (shouldRequestCameraPermission) {
            setRequestCameraPermissionFalse()
            cameraPermissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(shouldOpenCameraApp) {
        if (shouldOpenCameraApp) {
            setOpenCameraAppFalse()
            if (uriForTakePhoto != Uri.EMPTY) {
                cameraLauncher.launch(uriForTakePhoto)
            }
        }
    }

    if (shouldOpenAppSettings) {
        setShouldOpenAppSettingsFalse()
        val context = LocalContext.current
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        )
        context.startActivity(intent)
    }
}