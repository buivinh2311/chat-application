package com.example.feature_everybody.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.FriendRequest
import com.example.feature_everybody.usecase.AcceptFriendRequestUseCase
import com.example.feature_everybody.usecase.GetReceivedFriendRequestsUseCase
import com.example.feature_everybody.usecase.RejectFriendRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EveryBodyViewModel @Inject constructor(
    getReceivedFriendRequestsUseCase: GetReceivedFriendRequestsUseCase,
    private val acceptFriendRequestUseCase: AcceptFriendRequestUseCase,
    private val rejectFriendRequestUseCase: RejectFriendRequestUseCase
): ViewModel() {

    val friendReceivedRequest = getReceivedFriendRequestsUseCase()

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