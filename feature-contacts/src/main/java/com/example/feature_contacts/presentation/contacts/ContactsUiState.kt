package com.example.feature_contacts.presentation.contacts

import com.example.core_model.composite.FriendRequestWithUser

data class ContactsUiState(
    val friendRequestReceived: List<FriendRequestWithUser>
)