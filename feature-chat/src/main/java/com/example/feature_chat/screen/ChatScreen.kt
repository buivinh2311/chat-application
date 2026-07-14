package com.example.feature_chat.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_chat.component.ChatItem
import com.example.feature_chat.component.ChatStatus

@Composable
fun ChatScreen(
    onChatItemClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_chat)
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedAction = AppBottomBarAction.CHAT,
                onBottomActionClick = onBottomActionClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            contentPadding = PaddingValues(
                vertical = AppDimens.Space.Md
            )
        ) {
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Lg),
                    contentPadding = PaddingValues(
                        horizontal = AppDimens.Space.Lg
                    )
                ) {
                    items(
                        count = 12
                    ) {
                        ChatStatus()
                    }
                }
                Spacer(modifier = Modifier.height(AppDimens.Space.Md))
            }

            items(
                count = 10
            ) {
                ChatItem(
                    onClick = {
                        onChatItemClick("")
                    }
                )
            }
        }
    }
}