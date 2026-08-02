package com.example.core_domain.repository

import com.example.core_model.enum.DarkModeState
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getDarkModeStatus(): Flow<DarkModeState>
    suspend fun setDarkModeStatus(status: DarkModeState)
}