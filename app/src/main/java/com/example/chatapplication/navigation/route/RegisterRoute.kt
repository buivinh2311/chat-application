package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_auth.presentation.screen.RegisterScreen

@Composable
fun RegisterRoute(
    onNavigateToLogin: () -> Unit,
    onHasAccountClick: () -> Unit
) {
    RegisterScreen(
        onNavigateToLogin = onNavigateToLogin,
        onHasAccountClick = onHasAccountClick
    )
}