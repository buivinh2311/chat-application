package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_contacts.presentation.contacts.ContactsScreen

@Composable
fun ContactsRoute(
    onSearchClick: () -> Unit,
    onViewAllClick: () -> Unit,
    onViewMoreClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    ContactsScreen(
        onSearchClick = onSearchClick,
        onViewAllClick = onViewAllClick,
        onViewMoreClick = onViewMoreClick,
        onBottomActionClick = onBottomActionClick
    )
}