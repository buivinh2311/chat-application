package com.example.feature_chat.usecase

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class DownloadFileUseCase @Inject constructor() {
    suspend operator fun invoke(
        context: Context,
        fileUrl: String,
        fileName: String,
        mimeType: String
    ): Result<Long> = withContext(Dispatchers.IO) {
        runCatching {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(
                    MediaStore.Downloads.DISPLAY_NAME,
                    fileName
                )
                put(
                    MediaStore.Downloads.MIME_TYPE,
                    mimeType
                )
                put(
                    MediaStore.Downloads.RELATIVE_PATH,
                    Environment.DIRECTORY_DOWNLOADS
                )
                put(
                    MediaStore.Downloads.IS_PENDING,
                    1
                )
            }

            val uri = resolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: throw IOException("Cannot create MediaStore entry")

            try {
                resolver.openOutputStream(uri)?.use { output ->
                    URL(fileUrl).openStream().use { input ->
                        input.copyTo(output)
                    }
                } ?: throw IOException("Cannot open output stream")

                resolver.update(
                    uri,
                    ContentValues().apply {
                        put(
                            MediaStore.Downloads.IS_PENDING,
                            0
                        )
                    },
                    null,
                    null
                )
                ContentUris.parseId(uri)
            } catch (e: Exception) {
                resolver.delete(uri, null, null)
                throw e
            }
        }
    }
}