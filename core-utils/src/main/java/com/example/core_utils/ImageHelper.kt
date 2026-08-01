package com.example.core_utils

import android.content.Context
import android.net.Uri
import java.io.File

fun uriToFile(
    context: Context,
    uri: Uri
): File {

    val inputStream =
        context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Cannot open Uri")

    val file =
        File.createTempFile(
            "avatar",
            ".jpg",
            context.cacheDir
        )

    file.outputStream().use { output ->
        inputStream.copyTo(output)
    }
    return file
}