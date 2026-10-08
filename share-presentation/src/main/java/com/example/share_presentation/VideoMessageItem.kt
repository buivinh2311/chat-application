package com.example.share_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun VideoMessageItem(
    thumbnailUrl: String,
    width: Int,
    height: Int,
    reactionCounts: Map<String, Int>,
    isMine: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onReactionClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val maxImageWidth = maxWidth * 0.7f
        val maxImageHeight = 400.dp

        val originalWidth = width.toFloat()
        val originalHeight = height.toFloat()

        val widthRatio = maxImageWidth.value / originalWidth
        val heightRatio = maxImageHeight.value / originalHeight

        val scale = minOf(
            1f,
            widthRatio,
            heightRatio
        )

        val imageWidth = (originalWidth * scale).dp
        val imageHeight = (originalHeight * scale).dp
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
                AsyncImage(
                    model = thumbnailUrl,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.ic_image),
                    error = painterResource(R.drawable.ic_image),
                    modifier = Modifier
                        .size(
                            width = imageWidth,
                            height = imageHeight
                        )
                        .clip(RoundedCornerShape(AppDimens.Radius.Xl))
                        .border(
                            width = AppDimens.Border.Thin,
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(AppDimens.Radius.Sm)
                        )
                        .combinedClickable(
                            onClick = onClick,
                            onLongClick = onLongClick
                        )
                        .align(
                            alignment = if (isMine) Alignment.CenterEnd
                            else Alignment.CenterStart
                        ),
                    contentScale = ContentScale.Crop
                )

                if(reactionCounts.isNotEmpty()) {
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

    if (reactionCounts.isNotEmpty()) {
        Spacer(
            modifier = Modifier.height(AppDimens.Space.Lg)
        )
    }
}