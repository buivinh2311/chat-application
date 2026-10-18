package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_conversation.presentation.forward.ForwardScreen

@Composable
fun ForwardRoute(
    conversationId: String,
    messageId: String,
    onBack: () -> Unit
) {
    ForwardScreen(
        conversationId = conversationId,
        messageId = messageId,
        onBack = onBack
    )
}