package com.example.feature_everybody.presentation

import com.example.core_model.composite.FriendRequestWithUser

data class EverybodyUiState(
    val friendRequestReceived: List<FriendRequestWithUser>
)
