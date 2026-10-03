package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_conversation.presentation.creategroup.CreateGroupScreen

@Composable
fun CreateGroupRoute(
    friendId: String,
    onNavigationToConversationDetail: (String) -> Unit,
    onBackClick: () -> Unit
) {
    CreateGroupScreen(
        currentFriendId = friendId,
        onNavigationToConversationDetail = onNavigationToConversationDetail,
        onBackClick = onBackClick
    )
}