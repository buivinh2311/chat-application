package com.example.feature_auth.presentation.register

import com.example.core_model.result.RegisterResult

data class RegisterState(
    val isLoading: Boolean = false,
    val error: RegisterResult.Failure? = null,
    val isFullNameBlank: Boolean = false,
    val isEmailBlank: Boolean = false,
    val isEmailTypeError: Boolean = false,
    val isPasswordBlank: Boolean = false,
    val isNotStrongPassword: Boolean = false,
    val isRepeatPasswordBlank: Boolean = false,
    val isRepeatPasswordError: Boolean = false
)