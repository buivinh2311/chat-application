package com.example.feature_conversation.presentation.creategroup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_model.User
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R

@Composable
fun PickGroupMemberBottomBar(
    groupMember: List<User>,
    onRemoveMember: (User) -> Unit,
    onCreateGroup: () -> Unit
) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(end = AppDimens.Space.Lg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyRow(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = PaddingValues(
                    vertical = AppDimens.Space.Sm,
                    horizontal = AppDimens.Space.Lg
                ),
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
            ) {
                items(
                    count = groupMember.size,
                    key = { index -> groupMember[index].id }
                ) { index ->
                    val member = groupMember[index]
                    Box {
                        AsyncImage(
                            model = member.avatarUrl,
                            contentDescription = null,
                            placeholder = painterResource(R.drawable.account),
                            error = painterResource(R.drawable.account),
                            modifier = Modifier
                                .size(AppDimens.ImageSize.Sm)
                                .aspectRatio(1f)
                                .clip(shape = CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Icon(
                            painter = AppIcons.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .offset(
                                    x = AppDimens.Space.Xxs,
                                    y = -AppDimens.Space.Xxs
                                )
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .size(AppDimens.Icon.Xxs)
                                .align(Alignment.TopEnd)
                                .border(
                                    width = AppDimens.Border.Thick,
                                    color = MaterialTheme.colorScheme.background,
                                    shape = CircleShape
                                )
                                .padding(AppDimens.Space.Xs)
                                .clickable {
                                    onRemoveMember(member)
                                }
                        )
                    }
                }
            }

            Button(
                onClick = onCreateGroup,
                modifier = Modifier
                    .padding(
                        start = AppDimens.Space.Lg,
                        top = AppDimens.Space.Sm,
                        bottom = AppDimens.Space.Sm
                    )
                    .size(AppDimens.ButtonSize.Md)
                    .aspectRatio(1f)
                    .clip(shape = CircleShape),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    painter = AppIcons.Continue,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(AppDimens.Icon.Md)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimens.Space.Sm)
                .align(Alignment.TopCenter)
                .offset(y = -AppDimens.Space.Sm)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.08f)
                        )
                    )
                )
        )
    }
}