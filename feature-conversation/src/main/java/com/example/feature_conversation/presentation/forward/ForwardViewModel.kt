package com.example.feature_conversation.presentation.forward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Message
import com.example.core_model.composite.ConversationWithInfo
import com.example.core_ui.state.UiState
import com.example.feature_conversation.domain.usecase.ForwardMessageUseCase
import com.example.feature_conversation.domain.usecase.GetMessageByIdUseCase
import com.example.feature_conversation.domain.usecase.ObserveConversationsWithInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForwardViewModel @Inject constructor(
    private val getMessageByIdUseCase: GetMessageByIdUseCase,
    private val observeConversationsWithInfoUseCase: ObserveConversationsWithInfoUseCase,
    private val forwardMessageUseCase: ForwardMessageUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ForwardUiState>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ForwardEvent>()
    val event = _event.asSharedFlow()

    fun loadData(
        conversationId: String,
        messageId: String
    ) {
        viewModelScope.launch {
            val messageResult = getMessageByIdUseCase(
                conversationId = conversationId,
                messageId = messageId
            )

            messageResult.onFailure {
                _uiState.value = UiState.Error
                return@launch
            }

            val message = messageResult.getOrNull() ?: run {
                _uiState.value = UiState.Error
                return@launch
            }

            observeConversationsWithInfoUseCase()
                .catch {
                    _uiState.value = UiState.Error
                }
                .collect { conversationWithInfos ->
                    _uiState.value = if(conversationWithInfos.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(
                            ForwardUiState(
                                message = message,
                                conversationWithInfos = conversationWithInfos,
                                conversationWithInfoSelected = emptyList()
                            )
                        )
                    }
                }
        }
    }


    fun addConversationWithInfo(
        conversationWithInfo: ConversationWithInfo
    ) {
        viewModelScope.launch {
            _uiState.update { state ->
                when (state) {
                    is UiState.Success -> {
                        val conversationWithInfoSelected =
                            state.data.conversationWithInfoSelected + conversationWithInfo
                        UiState.Success(
                            state.data.copy(
                                conversationWithInfoSelected = conversationWithInfoSelected
                            )
                        )
                    }

                    else -> state
                }
            }
        }
    }

    fun removeConversationWithInfo(
        conversationWithInfo: ConversationWithInfo
    ) {
        viewModelScope.launch {
            _uiState.update { state ->
                when (state) {
                    is UiState.Success -> {
                        val conversationWithInfoSelected =
                            state.data.conversationWithInfoSelected - conversationWithInfo
                        UiState.Success(
                            state.data.copy(
                                conversationWithInfoSelected = conversationWithInfoSelected
                            )
                        )
                    }

                    else -> state
                }
            }
        }
    }

    fun forwardMessage(
        conversationWithInfos: List<ConversationWithInfo>,
        message: Message,
        accompanyingText: String
    ) {
        viewModelScope.launch {
            forwardMessageUseCase(
                conversationWithInfos = conversationWithInfos,
                message = message,
                accompanyingText = accompanyingText
            )
                .onSuccess {
                    _event.emit(ForwardEvent.ForwardSuccess)
                }
                .onFailure {
                    _event.emit(ForwardEvent.ShowError)
                }
        }
    }
}