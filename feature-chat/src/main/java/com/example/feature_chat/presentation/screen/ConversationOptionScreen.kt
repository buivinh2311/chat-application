package com.example.feature_chat.presentation.screen

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.ErrorScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.showToast
import com.example.core_ui.state.UiState
import com.example.core_utils.uriToFile
import com.example.feature_chat.domain.model.ConversationDirectOption
import com.example.feature_chat.domain.model.ConversationDirectOptionAction
import com.example.feature_chat.domain.model.ConversationGroupOption
import com.example.feature_chat.domain.model.ConversationGroupOptionAction
import com.example.feature_chat.presentation.component.ChangeGroupNameDialog
import com.example.feature_chat.presentation.component.ConversationOptionDirect
import com.example.feature_chat.presentation.component.ConversationOptionGroup
import com.example.feature_chat.presentation.component.ConversationOptionItem
import com.example.feature_chat.presentation.component.TurnOffNotificationBottomSheet
import com.example.feature_chat.presentation.event.ConversationDetailEvent
import com.example.feature_chat.presentation.event.ConversationOptionEvent
import com.example.feature_chat.presentation.uistate.ConversationDetailData
import com.example.feature_chat.presentation.viewmodel.ConversationOptionViewModel

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun ConversationOptionScreen(
    conversationId: String,
    onBackClick: () -> Unit,
    onChangeBackgroundImage: () -> Unit,
    onTurnOffNotification: () -> Unit,
    onTurnOnNotification: () -> Unit,
    onViewMedia: () -> Unit,
    onCreateGroup: (String) -> Unit,
    onBlock: () -> Unit,
    onDeleteChatHistory: () -> Unit,
    onUnFriend: () -> Unit
) {
    val conversationOptionViewModel: ConversationOptionViewModel = hiltViewModel()
    val uiState by conversationOptionViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(conversationId) {
        conversationOptionViewModel.loadConversation(conversationId)
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        conversationOptionViewModel.event.collect { event ->
            when (event) {
                ConversationOptionEvent.ShowError -> {
                    showToast(
                        context,
                        message = context.getString(R.string.text_unknown_error)
                    )
                }
            }
        }
    }

    var showTurnOffNotification by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_option),
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
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding)
                        .imePadding()
                        .fillMaxSize(),
                    contentPadding = PaddingValues(
                        vertical = AppDimens.Space.Xl
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        val conversationOptionUiState = state.data
                        when (val data = conversationOptionUiState.conversationDetailData) {
                            is ConversationDetailData.Direct -> {
                                ConversationOptionDirect(
                                    user = data.user,
                                    onChangeBackgroundImage = onChangeBackgroundImage,
                                    onTurnOffNotification = {
                                        showTurnOffNotification = true
                                    },
                                    onTurnOnNotification = onTurnOnNotification,
                                    onViewMedia = onViewMedia,
                                    onCreateGroup = { userId ->
                                        onCreateGroup(userId)
                                    },
                                    onBlock = onBlock,
                                    onDeleteChatHistory = onDeleteChatHistory,
                                    onUnFriend = onUnFriend
                                )
                            }

                            is ConversationDetailData.Group -> {
                                val group = data.group
                                val users = data.users
                                ConversationOptionGroup(
                                    group = group,
                                    onChangeGroupAvatar = { file ->
                                        conversationOptionViewModel
                                            .changeGroupAvatar(group.id, file)
                                    },
                                    onChangeBackgroundImage = onChangeBackgroundImage,
                                    onTurnOffNotification = {
                                        showTurnOffNotification = true
                                    },
                                    onTurnOnNotification = onTurnOnNotification,
                                    onViewMedia = onViewMedia
                                )
                            }
                        }
                    }
                }

                if(showTurnOffNotification) {
                    TurnOffNotificationBottomSheet()
                }
            }
        }
    }
}