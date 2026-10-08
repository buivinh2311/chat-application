package com.example.core_ui.component

import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
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
    showDivider: Boolean = false,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val gradientColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
    val strokeWidth = AppDimens.Divider.Normal
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .drawBehind {
                if (showDivider) {
                    drawLine(
                        color = gradientColor,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = strokeWidth.toPx()
                    )
                }
            }
            .height(AppDimens.Layout.BottomBarHeight),
        containerColor = MaterialTheme.colorScheme.surface,
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
                            tint = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleSmall,
                            color = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
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
            icon = AppIcons.Conversation,
            action = AppBottomBarAction.CHAT
        ),
        AppBottomBarItem(
            title = stringResource(R.string.title_contacts),
            icon = AppIcons.Contacts,
            action = AppBottomBarAction.CONTACTS
        ),
        AppBottomBarItem(
            title = stringResource(R.string.title_explore),
            icon = AppIcons.Explore,
            action = AppBottomBarAction.EXPLORE
        ),
        AppBottomBarItem(
            title = stringResource(R.string.title_person),
            icon = AppIcons.Person,
            action = AppBottomBarAction.PERSON
        )
    )
}