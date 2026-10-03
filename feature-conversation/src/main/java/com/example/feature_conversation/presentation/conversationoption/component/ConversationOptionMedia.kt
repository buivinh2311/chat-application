package com.example.feature_conversation.presentation.conversationoption.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_ui.component.ButtonNavigateItem


@Composable
fun ConversationOptionMedia(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = AppDimens.Space.Es,
                shape = RoundedCornerShape(AppDimens.Space.Lg)
            ),
        shape = RoundedCornerShape(AppDimens.Space.Lg),
        color = MaterialTheme.colorScheme.surfaceContainerLowest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppDimens.Space.Lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonNavigateItem(
                title = stringResource(R.string.title_media),
                text = stringResource(R.string.description_media),
                icon = AppIcons.Media,
                iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onNavigate = {}
            )
            Spacer(modifier = Modifier.height(AppDimens.Space.Sm))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = AppDimens.Space.Xs,
                        horizontal = AppDimens.Space.Lg
                    ),
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Sm)
            ) {
                AsyncImage(
                    model = "",
                    placeholder = painterResource(R.drawable.account),
                    error = painterResource(R.drawable.account),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(AppDimens.Radius.Lg))
                )

                AsyncImage(
                    model = "",
                    placeholder = painterResource(R.drawable.account),
                    error = painterResource(R.drawable.account),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(AppDimens.Radius.Lg))
                )

                AsyncImage(
                    model = "",
                    placeholder = painterResource(R.drawable.account),
                    error = painterResource(R.drawable.account),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(AppDimens.Radius.Lg))
                )

                AsyncImage(
                    model = "",
                    placeholder = painterResource(R.drawable.account),
                    error = painterResource(R.drawable.account),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(AppDimens.Radius.Lg))
                )
            }
        }
    }
}