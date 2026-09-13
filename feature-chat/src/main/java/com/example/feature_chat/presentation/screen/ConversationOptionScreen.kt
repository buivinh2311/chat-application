package com.example.feature_chat.presentation.screen

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
import com.example.core_ui.state.UiState
import com.example.core_utils.uriToFile
import com.example.feature_chat.domain.model.ConversationDirectOption
import com.example.feature_chat.domain.model.ConversationDirectOptionAction
import com.example.feature_chat.domain.model.ConversationGroupOption
import com.example.feature_chat.domain.model.ConversationGroupOptionAction
import com.example.feature_chat.presentation.component.ChangeGroupNameDialog
import com.example.feature_chat.presentation.component.ConversationOptionItem
import com.example.feature_chat.presentation.uistate.ConversationDetailData
import com.example.feature_chat.presentation.viewmodel.ConversationOptionViewModel

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
    onUnfriend: () -> Unit
) {
    var showChangeGroupNameDialog by remember {
        mutableStateOf(false)
    }
    val conversationOptionViewModel: ConversationOptionViewModel = hiltViewModel()
    val uiState by conversationOptionViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(conversationId) {
        conversationOptionViewModel.loadConversation(conversationId)
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
                                val user = data.user
                                val displayName = "${user.firstName} ${user.lastName}"
                                AsyncImage(
                                    model = user.avatarUrl,
                                    contentDescription = null,
                                    placeholder = painterResource(R.drawable.account),
                                    error = painterResource(R.drawable.account),
                                    modifier = Modifier
                                        .fillMaxWidth(0.3f)
                                        .aspectRatio(1f)
                                        .clip(shape = CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                                Text(
                                    text = displayName,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(modifier = Modifier.height(AppDimens.Space.Xxl))

                                conversationDirectOptions(displayName, false).forEach { item ->
                                    ConversationOptionItem(
                                        icon = item.icon,
                                        title = item.title,
                                        onClick = {
                                            when (item.action) {
                                                ConversationDirectOptionAction.CHANGE_BACKGROUND_IMAGE ->
                                                    onChangeBackgroundImage()

                                                ConversationDirectOptionAction.TURN_OFF_NOTIFICATION ->
                                                    onTurnOffNotification()

                                                ConversationDirectOptionAction.TURN_ON_NOTIFICATION ->
                                                    onTurnOnNotification()

                                                ConversationDirectOptionAction.VIEW_MEDIA ->
                                                    onViewMedia()

                                                ConversationDirectOptionAction.CREATE_GROUP ->
                                                    onCreateGroup(user.id)

                                                ConversationDirectOptionAction.BLOCK ->
                                                    onBlock()

                                                ConversationDirectOptionAction.DELETE_CHAT_HISTORY ->
                                                    onDeleteChatHistory()

                                                ConversationDirectOptionAction.UNFRIEND ->
                                                    onUnfriend()
                                            }
                                        }
                                    )
                                }

                                if (showChangeGroupNameDialog) {
                                    ChangeGroupNameDialog(
                                        onDismiss = {
                                            showChangeGroupNameDialog = false
                                        },
                                        onChange = { groupName ->
                                            showChangeGroupNameDialog = false

                                        }
                                    )
                                }
                            }

                            is ConversationDetailData.Group -> {
                                val group = data.group
                                val users = data.users
                                Box {
                                    val context = LocalContext.current
                                    val launcher = rememberLauncherForActivityResult(
                                        contract = ActivityResultContracts.PickVisualMedia()
                                    ) { uri ->
                                        if (uri != null) {
                                            val file = uriToFile(context, uri)
                                            conversationOptionViewModel.uploadGroupAvatar("", file)
                                        }
                                    }
                                    AsyncImage(
                                        model = group.avatarUrl,
                                        contentDescription = null,
                                        placeholder = painterResource(R.drawable.account),
                                        error = painterResource(R.drawable.account),
                                        modifier = Modifier
                                            .fillMaxWidth(0.3f)
                                            .aspectRatio(1f)
                                            .clip(shape = CircleShape)
                                            .clickable {
                                                launcher.launch(
                                                    PickVisualMediaRequest(
                                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                                    )
                                                )
                                            },
                                        contentScale = ContentScale.Crop
                                    )

                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .offset(
                                                x = -AppDimens.Space.Xxs,
                                                y = AppDimens.Space.Xs
                                            )
                                            .background(
                                                Color.LightGray,
                                                shape = CircleShape
                                            )
                                            .border(
                                                width = AppDimens.Space.Xs,
                                                color = MaterialTheme.colorScheme.background,
                                                shape = CircleShape
                                            )
                                    ) {
                                        Icon(
                                            painter = AppIcons.Camera,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(AppDimens.Space.Sm)
                                                .size(AppDimens.Icon.Sm),
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                                Row(
                                    modifier = Modifier.clickable {

                                    },
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = group.name,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Spacer(modifier = Modifier.width(AppDimens.Space.Xs))

                                    Icon(
                                        painter = AppIcons.Edit,
                                        contentDescription = null,
                                        modifier = Modifier.size(AppDimens.Icon.Md)
                                    )
                                }
                                Spacer(modifier = Modifier.height(AppDimens.Space.Xxl))

                                conversationGroupOptions(false).forEach { item ->
                                    ConversationOptionItem(
                                        icon = item.icon,
                                        title = item.title,
                                        onClick = when (item.action) {
                                            ConversationGroupOptionAction.CHANGE_BACKGROUND_IMAGE -> onChangeBackgroundImage
                                            ConversationGroupOptionAction.TURN_OFF_NOTIFICATION -> onTurnOffNotification
                                            ConversationGroupOptionAction.TURN_ON_NOTIFICATION -> onTurnOnNotification
                                            ConversationGroupOptionAction.VIEW_MEDIA -> onViewMedia
                                            ConversationGroupOptionAction.VIEW_GROUP_CALENDAR -> onChangeBackgroundImage
                                            ConversationGroupOptionAction.CREATE_VOTE -> onChangeBackgroundImage
                                            ConversationGroupOptionAction.VIEW_MEMBER -> onChangeBackgroundImage
                                            ConversationGroupOptionAction.VIEW_GROUP_LINK -> onChangeBackgroundImage
                                            ConversationGroupOptionAction.DELETE_CHAT_HISTORY -> onChangeBackgroundImage
                                            ConversationGroupOptionAction.LEAVE_THE_GROUP -> onChangeBackgroundImage
                                        }
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

@Composable
fun conversationDirectOptions(
    displayName: String,
    isNotificationOn: Boolean
): List<ConversationDirectOption> {
    return listOf(
        ConversationDirectOption(
            icon = AppIcons.Paint,
            title = stringResource(R.string.title_change_backgroud_image),
            action = ConversationDirectOptionAction.CHANGE_BACKGROUND_IMAGE
        ),

        ConversationDirectOption(
            icon = AppIcons.NotificationOff,
            title = stringResource(R.string.title_turn_off_notification),
            action = ConversationDirectOptionAction.TURN_OFF_NOTIFICATION
        ),

        ConversationDirectOption(
            icon = AppIcons.NotificationOn,
            title = stringResource(R.string.title_turn_on_notification),
            action = ConversationDirectOptionAction.TURN_ON_NOTIFICATION
        ),

        ConversationDirectOption(
            icon = AppIcons.Media,
            title = stringResource(R.string.title_media),
            action = ConversationDirectOptionAction.VIEW_MEDIA
        ),

        ConversationDirectOption(
            icon = AppIcons.CreateGroup,
            title = stringResource(R.string.title_create_group, displayName),
            action = ConversationDirectOptionAction.CREATE_GROUP
        ),

        ConversationDirectOption(
            icon = AppIcons.Block,
            title = stringResource(R.string.title_block),
            action = ConversationDirectOptionAction.BLOCK
        ),

        ConversationDirectOption(
            icon = AppIcons.Delete,
            title = stringResource(R.string.title_delete_chat_history),
            action = ConversationDirectOptionAction.DELETE_CHAT_HISTORY
        ),

        ConversationDirectOption(
            icon = AppIcons.FriendCancel,
            title = stringResource(R.string.title_unfriend),
            action = ConversationDirectOptionAction.UNFRIEND
        )
    )
}

@Composable
fun conversationGroupOptions(
    isNotificationOn: Boolean
): List<ConversationGroupOption> {
    return listOf(
        ConversationGroupOption(
            icon = AppIcons.Paint,
            title = stringResource(R.string.title_change_backgroud_image),
            action = ConversationGroupOptionAction.CHANGE_BACKGROUND_IMAGE
        ),

        ConversationGroupOption(
            icon = AppIcons.NotificationOff,
            title = stringResource(R.string.title_turn_off_notification),
            action = ConversationGroupOptionAction.TURN_OFF_NOTIFICATION
        ),

        ConversationGroupOption(
            icon = AppIcons.NotificationOn,
            title = stringResource(R.string.title_turn_on_notification),
            action = ConversationGroupOptionAction.TURN_ON_NOTIFICATION
        ),

        ConversationGroupOption(
            icon = AppIcons.Media,
            title = stringResource(R.string.title_media),
            action = ConversationGroupOptionAction.VIEW_MEDIA
        ),

        ConversationGroupOption(
            icon = AppIcons.Calendar,
            title = stringResource(R.string.title_group_calendar),
            action = ConversationGroupOptionAction.VIEW_GROUP_CALENDAR
        ),

        ConversationGroupOption(
            icon = AppIcons.CreateVote,
            title = stringResource(R.string.title_create_vote),
            action = ConversationGroupOptionAction.CREATE_VOTE
        ),

        ConversationGroupOption(
            icon = AppIcons.ViewMember,
            title = stringResource(R.string.title_view_member),
            action = ConversationGroupOptionAction.VIEW_MEMBER
        ),

        ConversationGroupOption(
            icon = AppIcons.Link,
            title = stringResource(R.string.title_group_link),
            action = ConversationGroupOptionAction.VIEW_GROUP_LINK
        ),

        ConversationGroupOption(
            icon = AppIcons.Delete,
            title = stringResource(R.string.title_delete_chat_history),
            action = ConversationGroupOptionAction.DELETE_CHAT_HISTORY
        ),

        ConversationGroupOption(
            icon = AppIcons.FriendCancel,
            title = stringResource(R.string.title_unfriend),
            action = ConversationGroupOptionAction.LEAVE_THE_GROUP
        )
    )
}