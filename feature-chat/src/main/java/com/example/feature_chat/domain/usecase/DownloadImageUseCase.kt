package com.example.feature_chat.domain.usecase

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor() {
    suspend operator fun invoke(
        context: Context,
        imageUrl: String,
        fileName: String,
        mimeType: String
    ): Result<Long> = withContext(Dispatchers.IO) {
        runCatching {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    fileName
                )
                put(
                    MediaStore.Images.Media.MIME_TYPE,
                    mimeType
                )
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES
                )
            }

            val uri = resolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: throw IOException("Cannot create MediaStore entry")

            try {
                resolver.openOutputStream(uri)?.use { output ->
                    URL(imageUrl).openStream().use { input ->
                        input.copyTo(output)
                    }
                } ?: throw IOException("Cannot open output stream")

            } catch (e: Exception) {
                resolver.delete(uri, null, null)
                throw e
            }
        }
    }
}