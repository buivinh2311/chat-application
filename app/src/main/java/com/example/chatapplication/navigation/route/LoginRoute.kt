package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_auth.presentation.screen.LoginScreen

@Composable
fun LoginRoute(
    onCreateNewAccountClick: () -> Unit
) {
    LoginScreen(
        onCreateNewAccountClick = onCreateNewAccountClick
    )
}