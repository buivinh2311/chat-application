package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_auth.presentation.forgotpassword.ForgotPasswordScreen

@Composable
fun ForgotPasswordRoute(
    onNavigateToLogin: () -> Unit
) {
    ForgotPasswordScreen(
        onNavigateToLogin = onNavigateToLogin
    )
}