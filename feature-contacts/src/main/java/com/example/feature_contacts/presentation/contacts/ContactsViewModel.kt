package com.example.feature_contacts.presentation.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.FriendRequest
import com.example.core_ui.state.UiState
import com.example.feature_contacts.usecase.AcceptFriendRequestUseCase
import com.example.feature_contacts.usecase.ObserveFriendRequestsByReceiverIdUseCase
import com.example.feature_contacts.usecase.RejectFriendRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val observeFriendRequestsByReceiverIdUseCase: ObserveFriendRequestsByReceiverIdUseCase,
    private val acceptFriendRequestUseCase: AcceptFriendRequestUseCase,
    private val rejectFriendRequestUseCase: RejectFriendRequestUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ContactsUiState>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ContactsEvent>()
    val event = _event.asSharedFlow()

    fun loadEverybody() {
        viewModelScope.launch {
            combine(
                observeFriendRequestsByReceiverIdUseCase()
            ) { requestReceived ->
                ContactsUiState(
                    friendRequestReceived = requestReceived[0]
                )
            }
                .catch {
                    _uiState.value = UiState.Error
                }
                .collect { state ->
                    _uiState.value = UiState.Success(state)
                }
        }
    }

    fun acceptRequest(request: FriendRequest) {
        viewModelScope.launch {
            acceptFriendRequestUseCase(request)
        }
    }

    fun rejectRequest(requestId: String) {
        viewModelScope.launch {
            rejectFriendRequestUseCase(requestId)
        }
    }
}