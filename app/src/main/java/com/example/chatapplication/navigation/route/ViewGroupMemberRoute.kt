package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_chat.presentation.screen.ViewGroupMemberScreen

@Composable
fun ViewGroupMemberRoute(
    conversationId: String,
    onBackClick: () -> Unit
) {
    ViewGroupMemberScreen(
        conversationId = conversationId,
        onBackClick = onBackClick
    )
}