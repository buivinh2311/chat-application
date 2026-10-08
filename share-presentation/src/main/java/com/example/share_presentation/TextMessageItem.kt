package com.example.share_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp
import com.example.core_model.Message
import com.example.core_resource.AppDimens

@Composable
fun TextMessageItem(
    modifier: Modifier = Modifier,
    reactionCounts: Map<String, Int>,
    text: String,
    isMine: Boolean,
    onLongClick: () -> Unit,
    onReactionClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .align(
                    alignment = if (isMine) Alignment.CenterEnd
                    else Alignment.CenterStart
                )
                .padding(
                    horizontal = AppDimens.Space.Md
                )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .align(
                            if (isMine) Alignment.CenterEnd
                            else Alignment.CenterStart
                        )
                        .widthIn(min = AppDimens.Layout.MinTextMessageWidth)
                ) {
                    Box {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .widthIn(min = AppDimens.Layout.MinTextMessageWidth)
                                .clip(RoundedCornerShape(AppDimens.Radius.Sm))
                                .background(
                                    if (isMine) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    }
                                )
                                .border(
                                    width = AppDimens.Border.Thin,
                                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                                    shape = RoundedCornerShape(AppDimens.Radius.Sm)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            onLongClick()
                                        }
                                    )
                                }
                                .padding(
                                    vertical = AppDimens.Space.Sm,
                                    horizontal = AppDimens.Space.Md
                                ),
                            color = if (isMine) {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )

                        if (reactionCounts.isNotEmpty()) {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .offset(
                                        x = -AppDimens.Space.Sm,
                                        y = AppDimens.Space.Lg
                                    )
                                    .clip(RoundedCornerShape(AppDimens.Radius.Xl))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .border(
                                        width = AppDimens.Border.Thin,
                                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                                        shape = RoundedCornerShape(AppDimens.Radius.Xl)
                                    )
                                    .clickable {
                                        onReactionClick()
                                    }
                                    .padding(
                                        vertical = AppDimens.Space.Es,
                                        horizontal = AppDimens.Space.Xs + AppDimens.Space.Xxs
                                    ),
                                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Xs)
                            ) {
                                reactionCounts.forEach { (emoji, count) ->
                                    Text(
                                        text = "$emoji $count",
                                        fontSize = 12.5.sp,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (reactionCounts.isNotEmpty()) {
        Spacer(
            modifier = Modifier.height(AppDimens.Space.Lg)
        )
    }
}