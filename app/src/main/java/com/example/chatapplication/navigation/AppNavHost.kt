package com.example.chatapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapplication.navigation.route.ChangePasswordRoute
import com.example.chatapplication.navigation.route.ConversationDetailRoute
import com.example.chatapplication.navigation.route.ConversationOptionRoute
import com.example.chatapplication.navigation.route.ConversationRoute
import com.example.chatapplication.navigation.route.DarkModeRoute
import com.example.chatapplication.navigation.route.EverybodyRoute
import com.example.chatapplication.navigation.route.ForgotPasswordRoute
import com.example.chatapplication.navigation.route.FriendRequestRoute
import com.example.chatapplication.navigation.route.FriendSuggestionRoute
import com.example.chatapplication.navigation.route.LanguageRoute
import com.example.chatapplication.navigation.route.LoginRoute
import com.example.chatapplication.navigation.route.PickGroupMemberRoute
import com.example.chatapplication.navigation.route.ProfileRoute
import com.example.chatapplication.navigation.route.RegisterRoute
import com.example.chatapplication.navigation.route.SearchRoute
import com.example.chatapplication.navigation.route.SettingsRoute
import com.example.chatapplication.navigation.route.ViewGroupMemberRoute
import com.example.core_model.enum.ConversationType
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.presentation.menu.SettingOptionAction

@Composable
fun AppNavHost(
    startDestination: String
) {
    val navController = rememberNavController()

    var selectedAction: AppBottomBarAction by remember {
        mutableStateOf(AppBottomBarAction.CHAT)
    }

    val onSearchClick: () -> Unit = {
        navController.navigate(AppRoute.SEARCH)
    }

    val onScannerClick: () -> Unit = {
        navController.navigate(AppRoute)
    }

    val onNavigationToConversationDetail: (String) -> Unit = { conversationId ->
        navController.navigate("${AppRoute.CONVERSATION_DETAIL}/$conversationId")
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
                navController.navigate(AppRoute.CONVERSATION)
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

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = AppRoute.CONVERSATION) {
            ConversationRoute(
                onSearchClick = onSearchClick,
                onNavigationToConversationDetail = onNavigationToConversationDetail,
                onBottomActionClick = onBottomActionClick,
                onScannerClick = onScannerClick
            )
        }

        composable(route = AppRoute.CONVERSATION_DETAIL_WITH_ARG) { backStackEntry ->
            val conversationId = backStackEntry.arguments?.getString("conversationId")
                    ?: return@composable
            ConversationDetailRoute(
                conversationId = conversationId,
                onBackClick = onBackClick,
                onOptionClick = {
                    navController.navigate("${AppRoute.CONVERSATION_OPTION}/$conversationId")
                }
            )
        }

        composable(route = AppRoute.CONVERSATION_OPTION_WITH_ARG) { backStackEntry ->
            val conversationId = backStackEntry.arguments?.getString("conversationId")
                    ?: return@composable
            ConversationOptionRoute(
                conversationId = conversationId,
                onBackClick = onBackClick,
                onCreateGroup = { userId ->
                    navController.navigate("${AppRoute.PICK_GROUP_MEMBER}/${userId}")
                },
                onViewGroupMember = { conversationId ->
                    navController.navigate("${AppRoute.VIEW_GROUP_MEMBER}/${conversationId}")
                }
            )
        }

        composable(route = AppRoute.PICK_GROUP_MEMBER_WITH_ARG) { backStackEntry ->
            val friendId = backStackEntry.arguments?.getString("userId")
                ?: return@composable
            PickGroupMemberRoute(
                friendId = friendId,
                onNavigationToConversationDetail = { conversationId ->
                    onNavigationToConversationDetail(conversationId)
                },
                onBackClick = onBackClick
            )
        }

        composable(route = AppRoute.SEARCH) {
            SearchRoute(
                selectedAction = selectedAction,
                onBackClick = onBackClick,
                onNavigationToConversationDetail = onNavigationToConversationDetail,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(route = AppRoute.EVERYBODY) {
            EverybodyRoute(
                onSearchClick = onSearchClick,
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
                onSearchClick = onSearchClick,
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

        composable(route = AppRoute.LOGIN) { backStackEntry ->
            val showDialog = backStackEntry.arguments
                ?.getString("showDialog")?.toBoolean() ?: false
            LoginRoute(
                showDialog = showDialog,
                onNavigateToHome = {
                    navController.navigate(AppRoute.CONVERSATION)
                },
                onCreateNewAccountClick = onCreateNewAccountClick,
                onForgotPasswordClick = {
                    navController.navigate(AppRoute.FORGOT_PASSWORD)
                }
            )
        }

        composable(route = AppRoute.REGISTER) {
            RegisterRoute(
                onNavigateToLogin = {
                    navController.navigate("login?showDialog=true")
                },
                onHasAccountClick = onHasAccountClick
            )
        }

        composable(route = AppRoute.FORGOT_PASSWORD) {
            ForgotPasswordRoute(
                onNavigateToLogin = {
                    navController.navigate("login?showDialog=false")
                }
            )
        }

        composable(route = AppRoute.VIEW_GROUP_MEMBER_WITH_ARG) { backStackEntry ->
            val conversationId = backStackEntry.arguments?.getString("conversationId")
                ?: return@composable
            ViewGroupMemberRoute(
                conversationId = conversationId,
                onBackClick = onBackClick
            )
        }
    }
}