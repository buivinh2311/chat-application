package com.example.share_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.core_model.enum.UserStatus
import com.example.core_resource.AppDimens
import com.example.core_resource.R

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    id: String,
    name: String,
    avatarUrl: String?,
    status: UserStatus,
    onClick: (String) -> Unit
) {
    Surface(
        onClick = {
            onClick(id)
        },
        color = Color.Transparent,
        modifier = modifier
            .height(AppDimens.Layout.ChatItemHeight)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(
                    vertical = AppDimens.Space.Sm,
                    horizontal = AppDimens.Space.Lg
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.account),
                error = painterResource(R.drawable.account),
                modifier = Modifier
                    .fillMaxHeight()
                    .size(AppDimens.ImageSize.Md)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Md))

            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}