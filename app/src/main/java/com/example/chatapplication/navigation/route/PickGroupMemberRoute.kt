package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_chat.presentation.screen.PickGroupMemberScreen

@Composable
fun PickGroupMemberRoute(
    userId: String,
    onBackClick: () -> Unit
) {
    PickGroupMemberScreen(
        userId = userId,
        onBackClick = onBackClick
    )
}