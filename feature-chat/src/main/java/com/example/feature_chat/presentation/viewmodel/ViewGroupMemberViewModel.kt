package com.example.feature_chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_ui.state.UiState
import com.example.feature_chat.domain.usecase.GetCurrentUserIdUseCase
import com.example.feature_chat.domain.usecase.ObserveConversationByIdUseCase
import com.example.feature_chat.presentation.uistate.ConversationDetailData
import com.example.feature_chat.presentation.uistate.ViewGroupMemberUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewGroupMemberViewModel @Inject constructor(
    private val observeConversationByIdUseCase: ObserveConversationByIdUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ViewGroupMemberUiState>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    fun loadConversation(conversationId: String) {
        viewModelScope.launch {
            val currentUserId = getCurrentUserIdUseCase()
                .getOrElse {
                    _uiState.value = UiState.Error
                    return@launch
                }
            observeConversationByIdUseCase(conversationId)
                .catch {
                    _uiState.value = UiState.Error
                }
                .collect { data ->
                    when(data) {
                        is ConversationDetailData.Direct -> {
                            _uiState.value = UiState.Error
                        }

                        is ConversationDetailData.Group -> {
                            _uiState.value = UiState.Success(
                                ViewGroupMemberUiState(
                                    conversationData = data,
                                    currentUserId = currentUserId
                                )
                            )
                        }
                    }
                }
        }
    }
}