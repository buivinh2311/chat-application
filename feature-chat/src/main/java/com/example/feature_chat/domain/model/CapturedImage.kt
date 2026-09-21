package com.example.feature_chat.domain.model

import android.net.Uri
import java.io.File

data class CapturedImage(
    val file: File,
    val uri: Uri
)