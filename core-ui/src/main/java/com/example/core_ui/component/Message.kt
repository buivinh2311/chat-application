package com.example.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core_resource.AppDimens

@Composable
fun Message(
    modifier: Modifier = Modifier,
    content: String,
    isMine: Boolean
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .align(alignment = if(isMine) Alignment.CenterEnd else Alignment.CenterStart)
                .padding(
                    vertical = 1.dp,
                    horizontal = AppDimens.Space.Md
                )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(alignment = if(isMine) Alignment.CenterEnd else Alignment.CenterStart)
                        .clip(RoundedCornerShape(AppDimens.Radius.Lg))
                        .background(
                            color = if(isMine) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                        )
                        .padding(
                            vertical = AppDimens.Space.Sm,
                            horizontal = AppDimens.Space.Md
                        ),
                    color = if(isMine) Color.White else MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}