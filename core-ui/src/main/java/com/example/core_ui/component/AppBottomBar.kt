package com.example.core_ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core_resource.R
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.menu.AppBottomBarItem

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    selectedAction: AppBottomBarAction,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(AppDimens.Layout.BottomBarHeight),
        containerColor = MaterialTheme.colorScheme.background,
        actions = {
            appBottomBarItems().forEach { item ->
                val isSelected = item.action == selectedAction
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable(
                            indication = ripple(
                                bounded = false,
                                radius = AppDimens.Radius.Lg,
                                color = MaterialTheme.colorScheme.background.copy(alpha = 0.2f)
                            ),
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            onClick = {
                                onBottomActionClick(item.action)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = item.icon,
                            contentDescription = null,
                            modifier = Modifier.size(AppDimens.Icon.Sm),
                            tint = if(isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodySmall,
                            color = if(isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun appBottomBarItems(): List<AppBottomBarItem> {
    return listOf(
        AppBottomBarItem(
            title = stringResource(R.string.title_chat),
            icon = AppIcons.Chat,
            action = AppBottomBarAction.CHAT
        ),
        AppBottomBarItem(
            title = stringResource(R.string.title_everybody),
            icon = AppIcons.Everybody,
            action = AppBottomBarAction.EVERYBODY
        ),
        AppBottomBarItem(
            title = stringResource(R.string.title_settings),
            icon = AppIcons.Settings,
            action = AppBottomBarAction.SETTINGS
        )
    )
}