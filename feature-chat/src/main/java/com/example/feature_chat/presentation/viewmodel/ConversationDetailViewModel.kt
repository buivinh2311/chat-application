package com.example.feature_chat.presentation.viewmodel

import android.content.Context
import android.media.MediaMetadataRetriever
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Message
import com.example.core_model.MediaFile
import com.example.core_model.VideoMetadata
import com.example.core_ui.state.UiState
import com.example.feature_chat.presentation.event.ConversationDetailEvent
import com.example.feature_chat.presentation.screen.ConversationDetailData
import com.example.feature_chat.presentation.event.ConversationEvent
import com.example.feature_chat.usecase.DownloadImageUseCase
import com.example.feature_chat.usecase.GetConversationUseCase
import com.example.feature_chat.usecase.GetMessagesUseCase
import com.example.feature_chat.usecase.IsFriendUseCase
import com.example.feature_chat.usecase.LoadMoreMessagesUseCase
import com.example.feature_chat.usecase.MarkMessageAsReadUseCase
import com.example.feature_chat.usecase.ObserverFriendRequestUseCase
import com.example.feature_chat.usecase.RecallFriendRequestUseCase
import com.example.feature_chat.usecase.SendFileMessageUseCase
import com.example.feature_chat.usecase.SendFriendRequestUseCase
import com.example.feature_chat.usecase.SendImageMessageUseCase
import com.example.feature_chat.usecase.SendTextMessageUseCase
import com.example.feature_chat.usecase.SendVideoMessageUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ConversationDetailViewModel @Inject constructor(
    private val observerFriendRequestUseCase: ObserverFriendRequestUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val getConversationUseCase: GetConversationUseCase,
    private val recallFriendRequestUseCase: RecallFriendRequestUseCase,
    private val isFriendUseCase: IsFriendUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val loadMoreMessagesUseCase: LoadMoreMessagesUseCase,
    private val sendTextMessageUseCase: SendTextMessageUseCase,
    private val sendImageMessageUseCase: SendImageMessageUseCase,
    private val sendVideoMessageUseCase: SendVideoMessageUseCase,
    private val sendFileMessageUseCase: SendFileMessageUseCase,
    private val markMessageAsReadUseCase: MarkMessageAsReadUseCase,
    private val downloadImageUseCase: DownloadImageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ConversationDetailData>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ConversationDetailEvent>()
    val event = _event.asSharedFlow()

    fun loadConversation(conversationId: String) {
        viewModelScope.launch {
            val result = getConversationUseCase(conversationId)
            _uiState.value = if (result == null) {
                UiState.Empty
            } else {
                UiState.Success(result)
            }
        }
    }

    fun isFriend(uid: String) = isFriendUseCase(uid)

    fun isSentRequest(receiverId: String) = observerFriendRequestUseCase(receiverId)

    fun sendFriendRequest(receiverId: String) {
        viewModelScope.launch {
            val result = sendFriendRequestUseCase(receiverId)
            if (result.isFailure) {
                _event.emit(ConversationDetailEvent.ShowError)
            }
        }
    }

    fun recallFriendRequest(receiverId: String) {
        viewModelScope.launch {
            val result = recallFriendRequestUseCase(receiverId)
            if (result.isFailure) {
                _event.emit(ConversationDetailEvent.ShowError)
            }
        }
    }

    fun getMessage(conversationId: String) = getMessagesUseCase(conversationId)

    fun loadMoreMessage(
        conversationId: String,
        lastVisibleTimestamp: Timestamp
    ) {
        viewModelScope.launch {
            val messages = loadMoreMessagesUseCase(conversationId, lastVisibleTimestamp)
        }
    }

    fun sendTextMessage(
        conversationId: String,
        text: String
    ) {
        viewModelScope.launch {
            val result = sendTextMessageUseCase(conversationId, text)
            if (result.isFailure) {
                _event.emit(ConversationDetailEvent.ShowError)
            }
        }
    }

    fun sendMediaMessage(
        conversationId: String,
        media: List<MediaFile>
    ) {
        viewModelScope.launch {
            media.forEach { item ->
                when {
                    item.mimeType.startsWith("image/") ->
                        sendImageMessageUseCase(conversationId, item.file)

                    item.mimeType.startsWith("video/") ->
                        sendVideoMessageUseCase(conversationId, item.file)
                }
            }
        }
    }

    fun sendFileMessage(
        conversationId: String,
        files: List<File>
    ) {
        viewModelScope.launch {
            files.forEach { file ->
                sendFileMessageUseCase(conversationId, file)
            }
        }
    }

    fun markMessageAsRead(
        conversationId: String,
        messageId: String
    ) {
        viewModelScope.launch {
            markMessageAsReadUseCase(conversationId, messageId)
        }
    }

    fun downloadImage(
        context: Context,
        imageUrl: String,
        fileName: String
    ) {
        viewModelScope.launch {
            downloadImageUseCase(
                context = context,
                imageUrl = imageUrl,
                fileName = fileName
            )
            .onSuccess {
                _event.emit(ConversationDetailEvent.DownloadSuccess)
            }
            .onFailure {
                _event.emit(ConversationDetailEvent.DownloadFailed)
            }
        }
    }
}