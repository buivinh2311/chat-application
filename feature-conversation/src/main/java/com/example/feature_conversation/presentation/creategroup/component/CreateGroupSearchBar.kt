package com.example.feature_conversation.presentation.creategroup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R

@Composable
fun PickGroupMemberSearchBar(
    onSearch: (String) -> Unit
) {
    var keyword by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }
    val gradientHeight = AppDimens.Space.Sm

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.08f),
                            Color.Transparent
                        ),
                        startY = size.height,
                        endY = size.height + gradientHeight.toPx()
                    ),
                    topLeft = Offset(0f, size.height),
                    size = Size(size.width, gradientHeight.toPx())
                )
            }
            .padding(AppDimens.Space.Md)
            .clip(RoundedCornerShape(AppDimens.Radius.Sm))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(AppDimens.Space.Md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = AppIcons.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.width(AppDimens.Space.Sm))
        BasicTextField(
            value = keyword,
            onValueChange = {
                keyword = it
                onSearch(keyword)
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            cursorBrush = SolidColor(
                MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .weight(1f)
                .height(AppDimens.Space.Xl)
                .focusRequester(focusRequester),
            decorationBox = { innerTextField ->
                Box {
                    if (keyword.isEmpty()) {
                        Text(
                            text = stringResource(R.string.hint_query),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.6f
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}