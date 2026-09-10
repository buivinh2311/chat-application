package com.example.feature_chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.feature_chat.domain.usecase.GetAllConversationWithInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val getAllConversationWithInfoUseCase: GetAllConversationWithInfoUseCase
): ViewModel() {
    fun conversations() = getAllConversationWithInfoUseCase()
}