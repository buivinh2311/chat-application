package com.example.feature_settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction

@Composable
fun SettingsScreen(
    onSettingOptionClick: (SettingOptionAction) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_settings)
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedAction = AppBottomBarAction.SETTINGS,
                onBottomActionClick = onBottomActionClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(
                vertical = AppDimens.Space.Lg
            )
        ) {
            item {
                settingOptionItems().forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSettingOptionClick(item.action)
                            }
                            .padding(
                                vertical = AppDimens.Space.Md,
                                horizontal = AppDimens.Space.Lg
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = item.icon,
                            contentDescription = null,
                            modifier = Modifier.size(AppDimens.Icon.Md)
                        )
                        Spacer(modifier = Modifier.width(AppDimens.Space.Md))

                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun settingOptionItems() : List<SettingOptionItem> {
    return listOf(
        SettingOptionItem(
            icon = AppIcons.Account,
            title = stringResource(R.string.title_profile),
            action = SettingOptionAction.PROFILE
        ),

        SettingOptionItem(
            icon = AppIcons.Password,
            title = stringResource(R.string.title_password),
            action = SettingOptionAction.PASSWORD
        ),

        SettingOptionItem(
            icon = AppIcons.DarkMode,
            title = stringResource(R.string.title_dark_mode),
            action = SettingOptionAction.DARK_MODE
        ),

        SettingOptionItem(
            icon = AppIcons.Language,
            title = stringResource(R.string.title_language),
            action = SettingOptionAction.LANGUAGE
        ),

        SettingOptionItem(
            icon = AppIcons.Logout,
            title = stringResource(R.string.title_logout),
            action = SettingOptionAction.LOGOUT
        )
    )
}