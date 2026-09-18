package com.example.feature_chat.presentation.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_ui.component.AppIconButton

@Composable
fun PickGroupMemberGroupInfo(
    selectedAvatarUri: String?,
    groupName: String,
    isGroupNameBlank: Boolean,
    onSelectAvatar: (Uri) -> Unit,
    onGroupNameChange: (String) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            onSelectAvatar(uri)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.Space.Lg),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(selectedAvatarUri != null) {
            AsyncImage(
                model = selectedAvatarUri,
                contentDescription = null,
                placeholder = painterResource(R.drawable.account),
                error = painterResource(R.drawable.account),
                modifier = Modifier
                    .size(AppDimens.ImageSize.Sm)
                    .aspectRatio(1f)
                    .clip(shape = CircleShape)
                    .clickable {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                contentScale = ContentScale.Crop
            )
        } else {
            AppIconButton(
                icon = AppIcons.CameraFilled,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                onClick = {
                    launcher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            )
        }
        Spacer(modifier = Modifier.width(AppDimens.Space.Lg))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            BasicTextField(
                value = groupName,
                onValueChange = { onGroupNameChange(it) },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimens.Space.Xxl)
                    .clip(RoundedCornerShape(AppDimens.Radius.Sm))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = AppDimens.Space.Lg),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (groupName.isEmpty()) {
                            Text(
                                text = stringResource(R.string.hint_group_name),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground.copy(
                                    alpha = 0.5f
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )

            AnimatedVisibility(
                visible = isGroupNameBlank
            ) {
                Text(
                    text = stringResource(R.string.text_group_name_can_not_blank),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(
                            start = AppDimens.Space.Sm,
                            top = AppDimens.Space.Xs
                        )
                )
            }
        }
    }
}