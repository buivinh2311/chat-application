package com.example.feature_auth.presentation.login

import com.example.core_model.result.LoginResult
import com.example.core_model.result.ResendResult

data class LoginState (
    val isLoading: Boolean = false,
    val loginResult: LoginResult? = null,
    val resendResult: ResendResult? = null,
    val isEmailBlank: Boolean = false,
    val isPasswordBlank: Boolean = false
)