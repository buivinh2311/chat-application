package com.example.feature_chat.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.enum.ConversationType
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.ErrorScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.feature_chat.presentation.component.ConversationStatus
import com.example.feature_chat.presentation.viewmodel.ConversationViewModel
import com.example.share_presentation.ConversationItem

@Composable
fun ConversationScreen(
    onNavigationToConversationDetail: (String) -> Unit,
    onSearchClick: () -> Unit,
    onScannerClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val conversationViewModel: ConversationViewModel = hiltViewModel()
    val uiState by conversationViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        conversationViewModel.loadConversations()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_search),
                onSearchClick = onSearchClick,
                onScannerClick = onScannerClick
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedAction = AppBottomBarAction.CHAT,
                onBottomActionClick = onBottomActionClick
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            UiState.Loading -> {
                LoadingScreen()
            }

            UiState.Error -> {
                ErrorScreen(message = "ERROR")
            }

            UiState.Empty -> {

            }

            is UiState.Success -> {
                val data = state.data
                val conversations = data.conversationWithInfos
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding),
                    contentPadding = PaddingValues(
                        vertical = AppDimens.Space.Md
                    )
                ) {
                    items(
                        count = conversations.size,
                        key = { index -> conversations[index].conversation.id }
                    ) { index ->
                        ConversationItem(
                            conversationWithInfo = conversations[index],
                            onClick = { conversationId ->
                                onNavigationToConversationDetail(conversationId)
                            }
                        )
                    }
                }
            }
        }
    }
}