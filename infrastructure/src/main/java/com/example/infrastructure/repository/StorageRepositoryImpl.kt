package com.example.infrastructure.repository

import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.core_domain.repository.StorageRepository
import com.example.core_model.CloudinaryUploadResult
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class StorageRepositoryImpl @Inject constructor(
    private val mediaManager: MediaManager
) : StorageRepository {
    override suspend fun upload(file: File): Result<CloudinaryUploadResult> {
        return try {
            Log.e(
                "CloudinaryUpload",
                file.toString()
            )
            val resourceType = when (file.extension.lowercase()) {
                "jpg", "jpeg", "png", "webp", "gif" -> "image"
                "mp4", "mov", "avi", "mkv" -> "video"
                else -> "raw"
            }
            suspendCancellableCoroutine { continuation ->
                val requestId = mediaManager
                    .upload(file.absolutePath)
                    .unsigned("chat_media")
                    .option("resource_type", resourceType)
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
                            continuation.resume(
                                Result.success(result)
                            )
                        }

                        override fun onError(
                            requestId: String,
                            error: ErrorInfo
                        ) {
//                            continuation.resumeWithException(
//                                Exception(error.description)
//                            )
                            Log.e(
                                "CloudinaryUpload",
                                "requestId=$requestId, code=${error.code}, description=${error.description}"
                            )

                            continuation.resumeWithException(
                                Exception(
                                    "Cloudinary upload failed: " +
                                            "code=${error.code}, " +
                                            "description=${error.description}"
                                )
                            )
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
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}