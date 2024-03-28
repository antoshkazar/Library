package com.library.app.features.scanQr


import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView

@Composable
@Stable
fun ScanQrScreen(viewModel: ScanQrViewModel) {
    var compoundBarcodeView: CompoundBarcodeView? = null

    BackHandler {
        if (compoundBarcodeView != null) {
            compoundBarcodeView?.apply { barcodeView.stopDecoding() }
            compoundBarcodeView?.pause()
            viewModel.onClickBackButton()
        }
    }
    AndroidView(
        factory = { context ->
            compoundBarcodeView = CompoundBarcodeView(context)
            compoundBarcodeView?.resume()
            compoundBarcodeView.apply {
                val capture = CaptureManager(context as Activity, this)
                capture.initializeFromIntent(context.intent, null)
                this?.setStatusText("")
                capture.decode()

                this?.decodeSingle { result ->
                    result.text?.let { barCodeOrQr ->
                        this.barcodeView.stopDecoding()
                        this.pause()
                        viewModel.onCodeScanned(barCodeOrQr)
                    }
                }
                this?.resume()
            }
            compoundBarcodeView ?: CompoundBarcodeView(context)
        },
        modifier = Modifier
    )
}