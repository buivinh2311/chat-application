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
import com.example.core_model.User
import com.example.core_model.enum.UserStatus
import com.example.core_resource.AppDimens
import com.example.core_resource.R

@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    user: User,
    onClick: (User) -> Unit
) {
    Surface(
        onClick = {
            onClick(user)
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
                    model = user.avatarUrl,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.account),
                    error = painterResource(R.drawable.account),
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(AppDimens.ImageSize.Md)
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )

                if(user.status == UserStatus.ONLINE) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(AppDimens.Icon.Xs)
                            .clip(shape = CircleShape)
                            .background(color = Color(0xFF31A24C))
                            .border(
                                width = AppDimens.Border.Thick,
                                color = MaterialTheme.colorScheme.background,
                                shape = CircleShape
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = user.firstName + " " + user.lastName,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "last message",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

            Text(
                text = "last day",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}