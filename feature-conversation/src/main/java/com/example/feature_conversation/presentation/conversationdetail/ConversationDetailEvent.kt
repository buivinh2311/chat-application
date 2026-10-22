package com.example.feature_conversation.presentation.conversationdetail

sealed interface ConversationDetailEvent {
    data object ShowError: ConversationDetailEvent
    data object DownloadSuccess: ConversationDetailEvent
    data object DownloadFailed: ConversationDetailEvent
    data object OpenFileFailed: ConversationDetailEvent
    data object PinnedMessageMaximum: ConversationDetailEvent
}