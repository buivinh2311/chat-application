package com.example.feature_auth.presentation.state

import com.example.core_model.result.ResetPasswordResult

data class ForgotPasswordState (
    val isEmailBlank: Boolean = false,
    val sendEmailResult: ResetPasswordResult? = null
)