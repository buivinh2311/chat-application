package com.example.feature_profile.presentation.profile

import com.example.core_model.User

data class ProfileUiState (
    val currentUser: User,
    val isFullNameBlank: Boolean = false
)