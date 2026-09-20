package com.example.feature_chat.domain.usecase

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import java.io.IOException
import javax.inject.Inject

class OpenFileUseCase @Inject constructor() {
    operator fun invoke(
        context: Context,
        fileName: String,
        mimeType: String
    ): Result<Unit> {
        return runCatching {
            val mediaStoreId = getMediaStoreId(
                context = context,
                fileName = fileName,
                mimeType = mimeType
            ) ?: throw IOException("File not found")
            val uri = ContentUris.withAppendedId(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                mediaStoreId
            )
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, mimeType)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(intent)
        }
    }

    fun getMediaStoreId(
        context: Context,
        fileName: String,
        mimeType: String
    ): Long? {
        val projection = arrayOf(
            MediaStore.Downloads._ID
        )

        val selection = buildString {
            append("${MediaStore.Downloads.DISPLAY_NAME} = ?")
            append(" AND ${MediaStore.Downloads.MIME_TYPE} = ?")
            append(" AND ${MediaStore.Downloads.RELATIVE_PATH} = ?")
        }

        val selectionArgs = arrayOf(
            fileName,
            mimeType,
            Environment.DIRECTORY_DOWNLOADS + "/"
        )

        context.contentResolver.query(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getLong(
                    cursor.getColumnIndexOrThrow(MediaStore.Downloads._ID)
                )
            }
        }

        return null
    }
}