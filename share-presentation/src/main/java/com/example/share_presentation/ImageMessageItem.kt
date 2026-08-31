package com.example.share_presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core_resource.R
import coil.compose.AsyncImage
import com.example.core_resource.AppDimens

@Composable
fun ImageMessageItem(
    fileUrl: String,
    width: Int,
    height: Int,
    isMine: Boolean,
    onClick: () -> Unit
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
                    model = fileUrl,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.ic_image),
                    error = painterResource(R.drawable.ic_image),
                    modifier = Modifier
                        .size(
                            width = imageWidth,
                            height = imageHeight
                        )
                        .clip(RoundedCornerShape(AppDimens.Radius.Md))
                        .align(
                            alignment = if (isMine) Alignment.CenterEnd
                            else Alignment.CenterStart
                        )
                        .clickable {
                            onClick()
                        },
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}