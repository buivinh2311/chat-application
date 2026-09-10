package com.example.feature_chat.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_chat.domain.model.FileInfo
import com.example.core_ui.state.UiState
import com.example.feature_chat.presentation.event.ConversationDetailEvent
import com.example.feature_chat.presentation.screen.ConversationDetailData
import com.example.feature_chat.domain.usecase.DownloadFileUseCase
import com.example.feature_chat.domain.usecase.DownloadImageUseCase
import com.example.feature_chat.domain.usecase.DownloadVideoUseCase
import com.example.feature_chat.domain.usecase.GetConversationUseCase
import com.example.feature_chat.domain.usecase.GetMessagesUseCase
import com.example.feature_chat.domain.usecase.IsFriendUseCase
import com.example.feature_chat.domain.usecase.LoadMoreMessagesUseCase
import com.example.feature_chat.domain.usecase.MarkMessageAsReadUseCase
import com.example.feature_chat.domain.usecase.ObserverFriendRequestUseCase
import com.example.feature_chat.domain.usecase.OpenFileUseCase
import com.example.feature_chat.domain.usecase.RecallFriendRequestUseCase
import com.example.feature_chat.domain.usecase.SendFileMessageUseCase
import com.example.feature_chat.domain.usecase.SendFriendRequestUseCase
import com.example.feature_chat.domain.usecase.SendImageMessageUseCase
import com.example.feature_chat.domain.usecase.SendTextMessageUseCase
import com.example.feature_chat.domain.usecase.SendVideoMessageUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
    private val downloadImageUseCase: DownloadImageUseCase,
    private val downloadVideoUseCase: DownloadVideoUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
    private val openFileUseCase: OpenFileUseCase
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
        fileInfos: List<FileInfo>
    ) {
        viewModelScope.launch {
            fileInfos.forEach { fileInfo ->
                when {
                    fileInfo.mimeType.startsWith("image/") ->
                        sendImageMessageUseCase(conversationId, fileInfo)

                    fileInfo.mimeType.startsWith("video/") ->
                        sendVideoMessageUseCase(conversationId, fileInfo)
                }
            }
        }
    }

    fun sendFileMessage(
        conversationId: String,
        fileInfos: List<FileInfo>
    ) {
        viewModelScope.launch {
            fileInfos.forEach { fileInfo ->
                sendFileMessageUseCase(conversationId, fileInfo)
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
        fileName: String,
        mimeType: String
    ) {
        viewModelScope.launch {
            downloadImageUseCase(
                context = context,
                imageUrl = imageUrl,
                fileName = fileName,
                mimeType = mimeType
            )
            .onSuccess {
                _event.emit(ConversationDetailEvent.DownloadSuccess)
            }
            .onFailure {
                _event.emit(ConversationDetailEvent.DownloadFailed)
            }
        }
    }

    fun downloadVideo(
        context: Context,
        videoUrl: String,
        fileName: String,
        mimeType: String
    ) {
        viewModelScope.launch {
            downloadVideoUseCase(
                context = context,
                videoUrl = videoUrl,
                fileName = fileName,
                mimeType = mimeType
            )
                .onSuccess {
                    _event.emit(ConversationDetailEvent.DownloadSuccess)
                }
                .onFailure {
                    _event.emit(ConversationDetailEvent.DownloadFailed)
                }
        }
    }

    fun downloadFile(
        context: Context,
        fileUrl: String,
        fileName: String,
        mimeType: String
    ) {
        viewModelScope.launch {
            downloadFileUseCase(
                context = context,
                fileUrl = fileUrl,
                fileName = fileName,
                mimeType = mimeType
            )
                .onSuccess {
                    _event.emit(ConversationDetailEvent.DownloadSuccess)
                }
                .onFailure {
                    _event.emit(ConversationDetailEvent.DownloadFailed)
                }
        }
    }

    fun openFile(
        context: Context,
        fileName: String,
        mimeType: String
    ) {
        viewModelScope.launch {
            openFileUseCase(
                context = context,
                fileName = fileName,
                mimeType = mimeType
            )
                .onFailure {
                    _event.emit(ConversationDetailEvent.OpenFileFailed)
                }
        }
    }
}