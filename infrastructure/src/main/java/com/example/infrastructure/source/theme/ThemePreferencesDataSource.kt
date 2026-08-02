package com.example.infrastructure.source.theme

import com.example.core_model.enum.DarkModeState
import kotlinx.coroutines.flow.Flow

interface ThemePreferencesDataSource {
    fun getDarkModeStatus(): Flow<DarkModeState>
    suspend fun saveDarkModeStatus(status: DarkModeState)
}