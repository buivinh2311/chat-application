package com.example.feature_chat.presentation.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.example.core_model.Group
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_utils.uriToFile
import com.example.feature_chat.domain.model.ConversationGroupOption
import com.example.feature_chat.domain.model.ConversationGroupOptionAction
import java.io.File

@Composable
fun ConversationOptionGroup(
    group: Group,
    onChangeGroupAvatar: (File) -> Unit,
    onChangeGroupName: (String, String) -> Unit,
    onChangeBackgroundImage: () -> Unit,
    onTurnOffNotification: () -> Unit,
    onTurnOnNotification: () -> Unit,
    onViewMedia: () -> Unit
) {
    var showChangeGroupNameDialog by remember {
        mutableStateOf(false)
    }

    Box {
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                val file = uriToFile(context, uri)
                onChangeGroupAvatar(file)
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
    Spacer(modifier = Modifier.height(AppDimens.Space.Xl))

    Row(
        modifier = Modifier.clickable {
            showChangeGroupNameDialog = true
        },
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = group.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Xs))

        Icon(
            painter = AppIcons.Edit,
            contentDescription = null,
            modifier = Modifier.size(AppDimens.Icon.Sm)
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

    if (showChangeGroupNameDialog) {
        ChangeGroupNameDialog(
            onDismiss = {
                showChangeGroupNameDialog = false
            },
            onChange = { groupName ->
                onChangeGroupName(group.id, groupName)
                showChangeGroupNameDialog = false
            }
        )
    }
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