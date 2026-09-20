package com.example.feature_chat.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.ErrorScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.feature_chat.presentation.component.ViewGroupMemberItem
import com.example.feature_chat.presentation.viewmodel.ConversationViewModel
import com.example.feature_chat.presentation.viewmodel.ViewGroupMemberViewModel
import com.example.share_presentation.ConversationItem

@Composable
fun ViewGroupMemberScreen(
    conversationId: String,
    onBackClick: () -> Unit
) {
    val viewGroupMemberViewModel: ViewGroupMemberViewModel = hiltViewModel()
    val uiState by viewGroupMemberViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(conversationId) {
        viewGroupMemberViewModel.loadConversation(conversationId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_group_member),
                onBackClick = onBackClick
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
                val conversationData = data.conversationData
                val users = conversationData.users.values.toList()
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding),
                    contentPadding = PaddingValues(
                        vertical = AppDimens.Space.Md
                    )
                ) {
                    items(
                        count = users.size,
                        key = { index -> users[index].id }
                    ) { index ->
                        val user = users[index]
                        ViewGroupMemberItem(
                            name = "${user.firstName} ${user.lastName}",
                            avatarUrl = user.avatarUrl
                        )
                    }
                }
            }
        }
    }
}