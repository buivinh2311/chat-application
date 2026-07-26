package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_auth.presentation.screen.LoginScreen

@Composable
fun LoginRoute(
    showDialog: Boolean = true,
    onNavigateToHome: () -> Unit,
    onCreateNewAccountClick: () -> Unit
) {
    LoginScreen(
        showDialog = showDialog,
        onNavigateToHome = onNavigateToHome,
        onCreateNewAccountClick = onCreateNewAccountClick
    )
}