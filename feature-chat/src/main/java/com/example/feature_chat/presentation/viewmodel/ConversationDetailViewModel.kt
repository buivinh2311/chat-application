package com.example.feature_chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_ui.state.UiState
import com.example.feature_chat.presentation.screen.ConversationDetailData
import com.example.feature_chat.presentation.event.ConversationEvent
import com.example.feature_chat.usecase.GetConversationUseCase
import com.example.feature_chat.usecase.IsFriendUseCase
import com.example.feature_chat.usecase.ObserverFriendRequestUseCase
import com.example.feature_chat.usecase.RecallFriendRequestUseCase
import com.example.feature_chat.usecase.SendFriendRequestUseCase
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
    private val isFriendUseCase: IsFriendUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ConversationDetailData>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ConversationEvent>()
    val event = _event.asSharedFlow()

    fun loadConversation(conversationId: String) {
        viewModelScope.launch {
            val result = getConversationUseCase(conversationId)
            _uiState.value = if(result == null) {
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
            if(result.isFailure) {
                _event.emit(ConversationEvent.ShowError)
            }
        }
    }

    fun recallFriendRequest(receiverId: String) {
        viewModelScope.launch {
            val result = recallFriendRequestUseCase(receiverId)
            if(result.isFailure) {
                _event.emit(ConversationEvent.ShowError)
            }
        }
    }
}