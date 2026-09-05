package com.example.feature_chat.usecase

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import javax.inject.Inject

class OpenFileUseCase @Inject constructor() {
    suspend operator fun invoke(
        context: Context,
        mediaStoreId: Long,
        mimeType: String
    ): Result<Unit> {
        return runCatching {
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
}