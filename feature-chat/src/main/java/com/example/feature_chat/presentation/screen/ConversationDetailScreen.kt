package com.example.feature_chat.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.core_model.enum.ConversationType
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppButton
import com.example.core_ui.component.Message
import com.example.core_ui.component.showToast
import com.example.core_ui.state.UiState
import com.example.feature_chat.presentation.component.ConversationDetailBottomBar
import com.example.feature_chat.presentation.component.ConversationDetailTopBar
import com.example.feature_chat.presentation.event.ConversationEvent
import com.example.feature_chat.presentation.viewmodel.ConversationDetailViewModel

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun ConversationDetailScreen(
    conversationId: String,
    onBackClick: () -> Unit
) {
    val conversationDetailViewModel: ConversationDetailViewModel = hiltViewModel()
    val uiState by conversationDetailViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(conversationId) {
        conversationDetailViewModel.loadConversation(conversationId)
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        conversationDetailViewModel.event.collect { event ->
            when (event) {
                ConversationEvent.ShowError -> {
                    showToast(
                        context,
                        message = context.getString(R.string.text_unknown_error)
                    )
                }
            }
        }
    }

    when (val state = uiState) {
        UiState.Loading -> {

        }

        UiState.Empty -> {

        }

        is UiState.Success -> {
            when(val data = state.data) {
                is ConversationDetailData.Direct -> {
                    val user = data.user
                    val conversation = data.conversation
                    val isFriend by conversationDetailViewModel
                        .isFriend(user.id)
                        .collectAsStateWithLifecycle(true)
                    val isSentRequest by conversationDetailViewModel
                        .isSentRequest(user.id)
                        .collectAsStateWithLifecycle(false)

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            ConversationDetailTopBar(
                                imageUrl = user.avatarUrl,
                                name = user.firstName + " " + user.lastName,
                                onBackClick = onBackClick
                            )
                        },
                        bottomBar = {
                            ConversationDetailBottomBar()
                        }
                    ) { innerPadding ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentPadding = PaddingValues(
                                top = AppDimens.Space.Xxl,
                                bottom = AppDimens.Space.Lg
                            )
                        ) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    AsyncImage(
                                        model = user.avatarUrl,
                                        contentDescription = null,
                                        placeholder = painterResource(R.drawable.account),
                                        error = painterResource(R.drawable.account),
                                        modifier = Modifier
                                            .fillMaxWidth(0.4f)
                                            .aspectRatio(1f)
                                            .clip(shape = CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                if(isFriend == false) {
                                    Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        AppButton(
                                            modifier = Modifier.fillMaxWidth(0.35f),
                                            label = if(!isSentRequest) stringResource(R.string.add_friend)
                                            else stringResource(R.string.recall),
                                            color = if(!isSentRequest) MaterialTheme.colorScheme.primary
                                            else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                                            onClick = {
                                                if(!isSentRequest) {
                                                    conversationDetailViewModel
                                                        .sendFriendRequest(user.id)
                                                } else {
                                                    conversationDetailViewModel
                                                        .recallFriendRequest(user.id)
                                                }
                                            }
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(AppDimens.Space.Xxl))
                            }

                            items(
                                count = 30
                            ) { index ->
                                if (index % 2 == 0) {
                                    Message(
                                        content = "message",
                                        isMine = false
                                    )
                                } else {
                                    Message(
                                        content = "message",
                                        isMine = true
                                    )
                                }
                            }
                        }
                    }
                }

                is ConversationDetailData.Group -> {
                    val group = data.group
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            ConversationDetailTopBar(
                                imageUrl = group.avatarUrl,
                                name = group.name,
                                onBackClick = onBackClick
                            )
                        },
                        bottomBar = {
                            ConversationDetailBottomBar()
                        }
                    ) { innerPadding ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentPadding = PaddingValues(
                                vertical = AppDimens.Space.Lg
                            )
                        ) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    AsyncImage(
                                        model = " ",
                                        contentDescription = null,
                                        placeholder = painterResource(R.drawable.account),
                                        error = painterResource(R.drawable.account),
                                        modifier = Modifier
                                            .fillMaxWidth(0.3f)
                                            .aspectRatio(1f)
                                            .clip(shape = CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Spacer(modifier = Modifier.height(AppDimens.Space.Lg))

                                AppButton(
                                    label = stringResource(R.string.add_friend),
                                    color = MaterialTheme.colorScheme.primary,
                                    onClick = {

                                    }
                                )
                            }

                            items(
                                count = 30
                            ) { index ->
                                if (index % 2 == 0) {
                                    Message(
                                        content = "message",
                                        isMine = false
                                    )
                                } else {
                                    Message(
                                        content = "message",
                                        isMine = true
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}