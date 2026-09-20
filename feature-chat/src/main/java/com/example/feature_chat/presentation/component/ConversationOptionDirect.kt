package com.example.feature_chat.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.core_model.User
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.feature_chat.domain.model.ConversationDirectOption
import com.example.feature_chat.domain.model.ConversationDirectOptionAction

@Composable
fun ConversationOptionDirect(
    user: User,
    onChangeBackgroundImage: () -> Unit,
    onTurnOffNotification: () -> Unit,
    onTurnOnNotification: () -> Unit,
    onViewMedia: () -> Unit,
    onCreateGroup: (String) -> Unit,
    onBlock: () -> Unit,
    onDeleteChatHistory: () -> Unit,
    onUnFriend: () -> Unit
) {
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
                        onUnFriend()
                }
            }
        )
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