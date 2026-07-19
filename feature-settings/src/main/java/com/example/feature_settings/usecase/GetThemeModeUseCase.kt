package com.example.feature_settings.usecase

import com.example.core_domain.repository.ThemeRepository
import com.example.core_model.DarkModeState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    operator fun invoke(): Flow<DarkModeState> {
        return repository.getDarkModeStatus()
    }
}