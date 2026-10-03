package com.example.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.example.core_resource.AppDimens

@Composable
fun AppIconTextButton(
    modifier: Modifier = Modifier,
    containerColor: Color? = null,
    icon: Painter,
    iconSize: Dp,
    iconColor: Color? = null,
    label: String,
    style: TextStyle,
    labelColor: Color? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(AppDimens.Radius.Xl))
            .background(containerColor ?: MaterialTheme.colorScheme.secondary)
            .clickable {
                onClick()
            }
            .padding(
                vertical = AppDimens.Space.Xs,
                horizontal = AppDimens.Space.Lg + AppDimens.Space.Xs
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = iconColor ?: MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Xs))

        Text(
            text = label,
            style = style,
            color = labelColor ?: MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center
        )
    }
}