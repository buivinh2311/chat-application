package com.example.chatapplication.navigation.route

import android.net.Uri
import androidx.compose.runtime.Composable
import com.example.feature_chat.presentation.screen.CameraScreen

@Composable
fun CameraRoute(
    onImageCaptured: (Uri) -> Unit,
    onBackClick: () -> Unit
) {
    CameraScreen(
        onSend = {},
        onBack = onBackClick
    )
}