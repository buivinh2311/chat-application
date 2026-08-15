package com.example.feature_chat.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.core_resource.AppDimens
import com.example.core_resource.R

@Composable
fun ConversationStatus(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AsyncImage(
                model = "",
                contentDescription = null,
                placeholder = painterResource(R.drawable.account),
                error = painterResource(R.drawable.account),
                modifier = Modifier
                    .fillMaxHeight()
                    .size(AppDimens.ImageSize.Xl)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .padding(
                        end = AppDimens.Space.Xs,
                        bottom = AppDimens.Space.Xs
                    )
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
        Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
    }
}