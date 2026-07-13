package com.example.share_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppButton

@Composable
fun FriendSuggestion(
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent
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
                        .size(16.dp)
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
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "person/group name",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Xs))

                Text(
                    text = "ban chung",
                    style = MaterialTheme.typography.bodySmall
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
                ) {
                    AppButton(
                        modifier = Modifier.weight(1f),
                        label = stringResource(R.string.add_friend),
                        color = MaterialTheme.colorScheme.primary,
                        onClick = { }
                    )

                    AppButton(
                        modifier = Modifier.weight(1f),
                        label = stringResource(R.string.label_remove),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                        onClick = { }
                    )
                }
            }
        }
    }
}