package com.example.chatapplication

import com.example.core_domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): FirebaseUser? {
        return repository.getCurrentUser()
    }
}