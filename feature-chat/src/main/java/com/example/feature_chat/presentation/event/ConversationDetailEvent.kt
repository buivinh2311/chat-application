package com.example.feature_chat.presentation.event

sealed interface ConversationDetailEvent {
    data object ShowError: ConversationDetailEvent
    data object DownloadSuccess: ConversationDetailEvent
    data object DownloadFailed: ConversationDetailEvent
}