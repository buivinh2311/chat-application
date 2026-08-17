package com.example.core_model.composite

import com.example.core_model.User

sealed class ConversationDisplayInfo {
    data class Direct(val otherUser: User) : ConversationDisplayInfo()
    data class Group(val group: com.example.core_model.Group) : ConversationDisplayInfo()
}