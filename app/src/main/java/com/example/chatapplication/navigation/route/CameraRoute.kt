package com.example.chatapplication.navigation.route

import android.net.Uri
import androidx.compose.runtime.Composable
import com.example.feature_conversation.presentation.camera.CameraScreen

@Composable
fun CameraRoute(
    onImageCaptured: (Uri) -> Unit,
    onBack: () -> Unit
) {
    CameraScreen(
        onSend = {},
        onBack = onBack
    )
}