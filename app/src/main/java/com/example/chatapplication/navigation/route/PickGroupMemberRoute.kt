package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_chat.presentation.screen.PickGroupMemberScreen

@Composable
fun PickGroupMemberRoute(
    friendId: String,
    onNavigationToConversationDetail: (String) -> Unit,
    onBackClick: () -> Unit
) {
    PickGroupMemberScreen(
        currentFriendId = friendId,
        onNavigationToConversationDetail = onNavigationToConversationDetail,
        onBackClick = onBackClick
    )
}