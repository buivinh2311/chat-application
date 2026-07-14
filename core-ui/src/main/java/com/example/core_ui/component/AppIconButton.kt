package com.example.core_ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.example.core_resource.AppDimens

@Composable
fun AppIconButton(
    icon: Painter,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .size(AppDimens.Icon.Sm)
                .clickable(
                    indication = ripple(
                        bounded = false,
                        radius = AppDimens.Ripple.Sm,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                    ),
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    onClick = onClick
                )
        )
    }
}