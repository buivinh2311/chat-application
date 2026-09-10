package com.example.feature_chat.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_ui.component.AppIconButton

@Composable
fun ConversationDetailTopBar(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    name: String,
    onBackClick: () -> Unit,
    onOptionClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                vertical = AppDimens.Space.Sm,
                horizontal = AppDimens.Space.Md,
            )
            .height(AppDimens.Layout.TopBarHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppIconButton(
            icon = AppIcons.Back,
            color = MaterialTheme.colorScheme.primary,
            onClick = onBackClick
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Lg))

        Box {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.account),
                error = painterResource(R.drawable.account),
                modifier = Modifier
                    .size(AppDimens.ImageSize.Sm)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .size(14.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color(0xFF31A24C))
                    .border(
                        width = AppDimens.Border.Thick,
                        color = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    )
            )
        }
        Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )

//            Text(
//                text = "last online",
//                style = MaterialTheme.typography.bodyMedium
//            )
        }

        AppIconButton(
            icon = AppIcons.Call,
            color = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Lg))

        AppIconButton(
            icon = AppIcons.VideoCall,
            color = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Lg))

        AppIconButton(
            icon = AppIcons.Option,
            color = MaterialTheme.colorScheme.primary,
            onClick = onOptionClick
        )
    }
}