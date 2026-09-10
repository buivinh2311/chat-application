package com.example.feature_chat.domain.usecase

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

class DownloadVideoUseCase @Inject constructor() {
    suspend operator fun invoke(
        context: Context,
        videoUrl: String,
        fileName: String,
        mimeType: String
    ): Result<Long> = withContext(Dispatchers.IO) {
        runCatching {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(
                    MediaStore.Video.Media.DISPLAY_NAME,
                    fileName
                )
                put(
                    MediaStore.Video.Media.MIME_TYPE,
                    mimeType
                )
                put(
                    MediaStore.Video.Media.RELATIVE_PATH,
                    Environment.DIRECTORY_MOVIES
                )
                put(
                    MediaStore.Video.Media.IS_PENDING,
                    1
                )
            }

            val uri = resolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: throw IOException("Cannot create MediaStore entry")

            try {
                resolver.openOutputStream(uri)?.use { output ->
                    URL(videoUrl).openStream().use { input ->
                        input.copyTo(output)
                    }
                } ?: throw IOException("Cannot open output stream")

                resolver.update(
                    uri,
                    ContentValues().apply {
                        put(
                            MediaStore.Video.Media.IS_PENDING,
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