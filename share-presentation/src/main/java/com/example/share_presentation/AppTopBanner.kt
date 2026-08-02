package com.example.share_presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.core_model.enum.BannerType
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons

@Composable
fun AppTopBanner(
    modifier: Modifier = Modifier,
    title: String,
    type: BannerType = BannerType.FAILURE
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = true,
            modifier = modifier
                .statusBarsPadding()
                .padding(top = AppDimens.Space.Lg)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.95f),
                shape = RoundedCornerShape(AppDimens.Radius.Sm),
                colors = CardDefaults.cardColors(
                    containerColor = when(type) {
                        BannerType.SUCCESS -> {
                            MaterialTheme.colorScheme.primaryContainer
                        }

                        BannerType.FAILURE -> {
                            MaterialTheme.colorScheme.errorContainer
                        }
                    }
                )
            ) {
                Row(
                    modifier = Modifier.padding(AppDimens.Space.Md),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = when(type) {
                            BannerType.SUCCESS -> {
                                AppIcons.Success
                            }

                            BannerType.FAILURE -> {
                                AppIcons.Failure
                            }
                        },
                        contentDescription = null,
                        tint = when(type) {
                            BannerType.SUCCESS -> {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            }

                            BannerType.FAILURE -> {
                                MaterialTheme.colorScheme.onErrorContainer
                            }
                        },
                        modifier = Modifier.size(AppDimens.Icon.Lg)
                    )
                    Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = when(type) {
                            BannerType.SUCCESS -> {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            }

                            BannerType.FAILURE -> {
                                MaterialTheme.colorScheme.onErrorContainer
                            }
                        }
                    )
                }
            }
        }
    }
}