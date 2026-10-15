package com.example.feature_settings.presentation.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.enum.DarkModeState
import com.example.core_model.enum.SettingOptionAction
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.ErrorScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.feature_settings.presentation.settings.component.SettingsAccount
import com.example.feature_settings.presentation.settings.component.SettingsHelpAndLogout
import com.example.feature_settings.presentation.settings.component.SettingsInterfaceAndExperience
import com.example.feature_settings.presentation.settings.component.SettingsNotificationAndSound
import com.example.feature_settings.presentation.settings.component.SettingsPersonalAndSecurity
import com.example.feature_settings.presentation.settings.component.SettingsStorageAndData

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun SettingsScreen(
    onSearchClick: () -> Unit,
    onSettingOptionClick: (SettingOptionAction) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        settingsViewModel.loadCurrentUser()
        settingsViewModel.event.collect { event ->
            when (event) {
                is SettingsEvent.ShowError -> {
                    showToast(
                        context,
                        message = context.getString(R.string.text_unknown_error)
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_individual),
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedAction = AppBottomBarAction.PERSON,
                onBottomActionClick = onBottomActionClick
            )
        }
    ) { innerPadding ->
        when(val state = uiState) {
            UiState.Loading -> {
                LoadingScreen()
            }

            UiState.Error -> {
                ErrorScreen(title = "error")
            }

            UiState.Empty -> {

            }

            is UiState.Success -> {
                val data = state.data
                val user = data.currentUser
                val darkModeState = data.darkModeState
                val isDarkMode = when(darkModeState) {
                    DarkModeState.ON -> true
                    DarkModeState.OFF -> false
                }
                LazyColumn(
                    modifier = Modifier.padding(innerPadding),
                    contentPadding = PaddingValues(AppDimens.Space.Md)
                ) {
                    item {
                        SettingsAccount(
                            context = context,
                            user = user,
                            uploadAvatar = { file ->
                                settingsViewModel.uploadAvatar(file)
                            },
                            onNavigateToQRCode = {
                                onSettingOptionClick(SettingOptionAction.QR_CODE)
                            },
                            onNavigateToProfile = {
                                onSettingOptionClick(SettingOptionAction.PROFILE)
                            }
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                        SettingsPersonalAndSecurity(
                            onNavigateToPassword = {
                                onSettingOptionClick(SettingOptionAction.PASSWORD)
                            },
                            onToggleMarkAsRead = { },
                            onToggleOnlineStatus = { }
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                        SettingsNotificationAndSound(
                            onTogglePushNotification = { },
                            onTogglePreview = { },
                            onToggleNotDisturb = { }
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                        SettingsInterfaceAndExperience(
                            isDarkMode = isDarkMode,
                            onToggleDarkMode = { isDarkMode ->
                                if(isDarkMode) {
                                    settingsViewModel.toggleThemeState(DarkModeState.ON)
                                } else {
                                    settingsViewModel.toggleThemeState(DarkModeState.OFF)
                                }
                            },
                            onNavigateToFontSize = {
                                onSettingOptionClick(SettingOptionAction.FONT_SIZE)
                            },
                            onNavigateToLanguage = {
                                onSettingOptionClick(SettingOptionAction.LANGUAGE)
                            }
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                        SettingsStorageAndData(
                            onNavigateToCacheMemory = {
                                onSettingOptionClick(SettingOptionAction.CACHE_MEMORY)
                            },
                            onToggleManagerFile = {}
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                        SettingsHelpAndLogout(
                            onNavigateToHelpCenter = {
                                onSettingOptionClick(SettingOptionAction.HELP_CENTER)
                            },
                            onLogout = {
                                settingsViewModel.logout()
                                onSettingOptionClick(SettingOptionAction.LOGOUT)
                            }
                        )
                    }
                }
            }
        }
    }
}