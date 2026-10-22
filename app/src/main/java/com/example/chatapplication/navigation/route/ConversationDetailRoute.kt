package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_conversation.presentation.conversationdetail.ConversationDetailScreen

@Composable
fun ConversationDetailRoute(
    conversationId: String,
    darkTheme: Boolean,
    onOption: (String) -> Unit,
    onOpenCamera: () -> Unit,
    onBack: () -> Unit,
    onForward: (String, String) -> Unit
) {
    ConversationDetailScreen(
        conversationId = conversationId,
        darkTheme = darkTheme,
        onBack = onBack,
        onOption = onOption,
        onOpenCamera = onOpenCamera,
        onForward = onForward
    )
}