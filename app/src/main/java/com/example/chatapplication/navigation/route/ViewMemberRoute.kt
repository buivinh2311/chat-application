package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_conversation.presentation.viewmember.ViewMemberScreen

@Composable
fun ViewMemberRoute(
    conversationId: String,
    onBack: () -> Unit
) {
    ViewMemberScreen(
        conversationId = conversationId,
        onBack = onBack
    )
}