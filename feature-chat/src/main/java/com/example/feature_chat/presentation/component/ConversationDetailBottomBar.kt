package com.example.feature_chat.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_ui.component.AppIconButton

@Composable
fun ConversationDetailBottomBar(
    modifier: Modifier = Modifier
) {
    var message by remember {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }
    LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
            .navigationBarsPadding()
            .padding(
                horizontal = AppDimens.Space.Md
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppIconButton(
            icon = AppIcons.File,
            color = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Md))

        AppIconButton(
            icon = AppIcons.Camera,
            color = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Md))

        AppIconButton(
            icon = AppIcons.Image,
            color = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Md))

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = AppDimens.Space.Xs)
                .clip(RoundedCornerShape(AppDimens.Radius.Xl))
                .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                .padding(
                    vertical = AppDimens.Space.Xs,
                    horizontal = AppDimens.Space.Sm
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = message,
                onValueChange = { message = it },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                cursorBrush = SolidColor(
                    MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                decorationBox = { innerTextField ->
                    Box {
                        if (message.isEmpty()) {
                            Text(
                                text = stringResource(R.string.hint_message),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

            AppIconButton(
                icon = AppIcons.Emoji,
                color = MaterialTheme.colorScheme.primary,
                onClick = { }
            )
        }
        Spacer(modifier = Modifier.width(AppDimens.Space.Md))

        AppIconButton(
            icon = AppIcons.Like,
            color = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
    }
}