package com.library.providers.fileProvider

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import java.io.IOException
import javax.inject.Inject

class FileProviderImpl @Inject constructor(
    private val context: Context,
) : FileProvider {

    @Throws(IOException::class)
    override fun getInputImageFromUri(uri: Uri): InputImage {
        try {
            return InputImage.fromFilePath(context, uri)
        } catch (e: IOException) {
            throw IOException("Error while converting")
        }
    }
}

interface FileProvider {
    fun getInputImageFromUri(uri: Uri): InputImage
}