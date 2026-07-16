package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_register.RegisterScreen

@Composable
fun RegisterRoute(
    onHasAccountClick: () -> Unit
) {
    RegisterScreen(
        onHasAccountClick = onHasAccountClick
    )
}