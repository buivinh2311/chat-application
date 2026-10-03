package com.example.feature_auth.presentation.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.result.ResetPasswordResult
import com.example.feature_auth.usecase.SendPasswordResetEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val sendPasswordResetEmailUseCase: SendPasswordResetEmailUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ForgotPasswordState())
    val uiState = _uiState.asStateFlow()

    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            val isEmailBlank = email.isBlank()
            _uiState.update {
                it.copy(
                    isEmailBlank = isEmailBlank,
                    sendEmailResult = null
                )
            }
            if(!isEmailBlank) {
                when(val result = sendPasswordResetEmailUseCase(email)) {
                    ResetPasswordResult.Success -> {
                        _uiState.update {
                            it.copy(
                                sendEmailResult = ResetPasswordResult.Success
                            )
                        }
                    }

                    is ResetPasswordResult.Failure -> {
                        _uiState.update {
                            it.copy(
                                sendEmailResult = result
                            )
                        }
                    }
                }
            }
        }
    }
}