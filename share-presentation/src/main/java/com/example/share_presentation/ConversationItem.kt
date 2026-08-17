package com.example.share_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.core_model.Conversation
import com.example.core_model.User
import com.example.core_model.composite.ConversationDisplayInfo
import com.example.core_model.composite.ConversationWithInfo
import com.example.core_model.enum.ConversationType
import com.example.core_model.enum.UserStatus
import com.example.core_resource.AppDimens
import com.example.core_resource.R

@Composable
fun ConversationItem(
    modifier: Modifier = Modifier,
    conversationWithInfo: ConversationWithInfo,
    onClick: (String) -> Unit
) {
    val conversation = conversationWithInfo.conversation
    val displayInfo = conversationWithInfo.displayInfo
    Surface(
        onClick = {
            onClick(conversation.id)
        },
        color = Color.Transparent,
        modifier = modifier.height(AppDimens.Layout.ChatItemHeight)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    vertical = AppDimens.Space.Sm,
                    horizontal = AppDimens.Space.Lg
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                AsyncImage(
                    model = when(displayInfo) {
                        is ConversationDisplayInfo.Direct -> displayInfo.otherUser.avatarUrl
                        is ConversationDisplayInfo.Group -> displayInfo.group.avatarUrl
                    },
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.account),
                    error = painterResource(R.drawable.account),
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(AppDimens.ImageSize.Md)
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )


//                if(conversation.status == UserStatus.ONLINE) {
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .size(AppDimens.Icon.Xs)
//                            .clip(shape = CircleShape)
//                            .background(color = Color(0xFF31A24C))
//                            .border(
//                                width = AppDimens.Border.Thick,
//                                color = MaterialTheme.colorScheme.background,
//                                shape = CircleShape
//                            )
//                    )
//                }
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = when(displayInfo) {
                        is ConversationDisplayInfo.Direct ->
                            displayInfo.otherUser.firstName + " " + displayInfo.otherUser.lastName

                        is ConversationDisplayInfo.Group -> displayInfo.group.name
                    },
                    style = MaterialTheme.typography.titleMedium
                )

                conversation.lastMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

            conversation.lastMessageTime?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}