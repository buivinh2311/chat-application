package com.example.share_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R

@Composable
fun PickContactItem(
    modifier: Modifier = Modifier,
    name: String,
    avatarUrl: String?,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(AppDimens.Layout.ChatItemHeight)
            .padding(horizontal = AppDimens.Space.Md)
            .shadow(
                elevation = AppDimens.Space.Es,
                shape = RoundedCornerShape(AppDimens.Space.Lg)
            ),
        onClick = {
            onCheckedChange()
        },
        shape = RoundedCornerShape(AppDimens.Space.Lg),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = AppDimens.Space.Md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.account),
                error = painterResource(R.drawable.account),
                modifier = Modifier
                    .size(AppDimens.ImageSize.Sm)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Md))

            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Md))

            Box(
                modifier = Modifier
                    .size(AppDimens.Ripple.Sm)
                    .clip(CircleShape)
                    .background(
                        if (checked) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                    .border(
                        width = AppDimens.Border.Normal,
                        color = if (checked) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (checked) {
                    Icon(
                        painter = AppIcons.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(AppDimens.Icon.Sm)
                    )
                }
            }
        }
    }
}