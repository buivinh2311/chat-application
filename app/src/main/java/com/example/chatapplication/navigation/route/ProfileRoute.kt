package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_profile.presentation.profile.ProfileScreen

@Composable
fun ProfileRoute(
    onBack: () -> Unit
) {
    ProfileScreen(
        onBack = onBack
    )
}