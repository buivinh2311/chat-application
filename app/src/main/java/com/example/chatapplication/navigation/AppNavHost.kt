package com.example.chatapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapplication.navigation.route.ChangePasswordRoute
import com.example.chatapplication.navigation.route.ChatDetailRoute
import com.example.chatapplication.navigation.route.ChatRoute
import com.example.chatapplication.navigation.route.DarkModeRoute
import com.example.chatapplication.navigation.route.EverybodyRoute
import com.example.chatapplication.navigation.route.FriendRequestRoute
import com.example.chatapplication.navigation.route.FriendSuggestionRoute
import com.example.chatapplication.navigation.route.LanguageRoute
import com.example.chatapplication.navigation.route.LoginRoute
import com.example.chatapplication.navigation.route.ProfileRoute
import com.example.chatapplication.navigation.route.RegisterRoute
import com.example.chatapplication.navigation.route.SettingsRoute
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.presentation.menu.SettingOptionAction

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val onChatItemClick: (String) -> Unit = { chatId ->
        navController.navigate("${AppRoute.CHAT_DETAIL}/$chatId")
    }

    val onViewAllClick: () -> Unit = {
        navController.navigate(AppRoute.FRIEND_REQUEST)
    }

    val onViewMoreClick: () -> Unit = {
        navController.navigate(AppRoute.FRIEND_SUGGESTION)
    }

    val onCreateNewAccountClick: () -> Unit = {
        navController.navigate(AppRoute.REGISTER)
    }

    val onHasAccountClick: () -> Unit = {
        navController.navigate(AppRoute.LOGIN)
    }

    val onBackClick: () -> Unit = {
        navController.navigateUp()
    }

    val onBottomActionClick: (AppBottomBarAction) -> Unit = { action ->
        when(action) {
            AppBottomBarAction.CHAT -> {
                navController.navigate(AppRoute.CHAT)
            }

            AppBottomBarAction.EVERYBODY -> {
                navController.navigate(AppRoute.EVERYBODY)
            }

            AppBottomBarAction.SETTINGS -> {
                navController.navigate(AppRoute.SETTINGS)
            }
        }
    }

    val onSettingOptionClick: (SettingOptionAction) -> Unit = { action ->
        when(action) {
            SettingOptionAction.PROFILE -> {
                navController.navigate(AppRoute.PROFILE)
            }

            SettingOptionAction.PASSWORD -> {
                navController.navigate(AppRoute.CHANGE_PASSWORD)
            }

            SettingOptionAction.DARK_MODE -> {
                navController.navigate(AppRoute.DARK_MODE)
            }

            SettingOptionAction.LANGUAGE -> {
                navController.navigate(AppRoute.LANGUAGE)
            }

            SettingOptionAction.LOGOUT -> {
                navController.navigate(AppRoute.LOGIN)
            }
        }
    }

    NavHost(navController = navController, startDestination = AppRoute.CHAT) {
        composable(route = AppRoute.CHAT) {
            ChatRoute(
                onChatItemClick = onChatItemClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(route = AppRoute.CHAT_DETAIL_WITH_ARG) {
            ChatDetailRoute(
                onBackClick = onBackClick
            )
        }

        composable(route = AppRoute.EVERYBODY) {
            EverybodyRoute(
                onViewAllClick = onViewAllClick,
                onViewMoreClick = onViewMoreClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(route = AppRoute.FRIEND_REQUEST) {
            FriendRequestRoute(
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(route = AppRoute.FRIEND_SUGGESTION) {
            FriendSuggestionRoute(
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(route = AppRoute.SETTINGS) {
            SettingsRoute(
                onSettingOptionClick = onSettingOptionClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(route = AppRoute.PROFILE) {
            ProfileRoute(
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(route = AppRoute.CHANGE_PASSWORD) {
            ChangePasswordRoute (
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(route = AppRoute.DARK_MODE) {
            DarkModeRoute(
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(route = AppRoute.LANGUAGE) {
            LanguageRoute(
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(route = AppRoute.LOGIN) {
            LoginRoute(
                onCreateNewAccountClick = onCreateNewAccountClick
            )
        }

        composable(route = AppRoute.REGISTER) {
            RegisterRoute(
                onHasAccountClick = onHasAccountClick
            )
        }
    }
}