package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_chat.screen.ChatDetailScreen

@Composable
fun ChatDetailRoute(
    onBackClick: () -> Unit
) {
    ChatDetailScreen(
        onBackClick = onBackClick
    )
}