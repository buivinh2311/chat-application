package com.example.infrastructure.repository

import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.core_domain.repository.StorageRepository
import com.example.core_model.CloudinaryUploadResult
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val mediaManager: MediaManager
) : StorageRepository {
    override suspend fun upload(file: File): CloudinaryUploadResult? {
        return suspendCancellableCoroutine { continuation ->
            val requestId = mediaManager
                .upload(file.absolutePath)
                .unsigned("chat_media")
                .option("resource_type", "auto")
                .callback(object : UploadCallback {

                    override fun onStart(
                        requestId: String
                    ) = Unit

                    override fun onProgress(
                        requestId: String,
                        bytes: Long,
                        totalBytes: Long
                    ) = Unit

                    override fun onSuccess(
                        requestId: String,
                        resultData: Map<Any?, Any?>
                    ) {
                        val result = CloudinaryUploadResult(
                            url = resultData["secure_url"] as String,
                            publicId = resultData["public_id"] as String,
                            resourceType = resultData["resource_type"] as String,
                            format = resultData["format"] as? String,
                            bytes = (resultData["bytes"] as Number).toLong(),
                            width = (resultData["width"] as? Number)?.toInt(),
                            height = (resultData["height"] as? Number)?.toInt(),
                            duration = (resultData["duration"] as? Number)?.toLong()
                        )
                        continuation.resume(result) { _, _, _ -> }
                    }

                    override fun onError(
                        requestId: String,
                        error: ErrorInfo
                    ) {
                        continuation.resume(null) { _, _, _ -> }
                    }

                    override fun onReschedule(
                        requestId: String,
                        error: ErrorInfo
                    ) = Unit
                })
                .dispatch()

            continuation.invokeOnCancellation {
                mediaManager.cancelRequest(requestId)
            }
        }
    }
}