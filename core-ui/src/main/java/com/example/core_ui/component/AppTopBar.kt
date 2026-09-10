package com.example.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String? = null,
    onSearchClick: (() -> Unit)? = null,
    onScannerClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(AppDimens.Layout.TopBarHeight)
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = AppDimens.Space.Lg),
        verticalAlignment = Alignment.CenterVertically
    ) {
        onBackClick?.let {
            AppIconButton(
                icon = AppIcons.Back,
                color = MaterialTheme.colorScheme.onPrimary,
                onClick = onBackClick
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Md))
        }

        if(onSearchClick != null) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onSearchClick()
                    }
                    .padding(vertical = AppDimens.Space.Sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = AppIcons.Search,
                    contentDescription = stringResource(R.string.title_search),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(AppDimens.Icon.Md)
                )
                Spacer(modifier = Modifier.width(AppDimens.Space.Lg))

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
                )
            }

            onScannerClick?.let {
                Spacer(modifier = Modifier.width(AppDimens.Space.Md))
                AppIconButton(
                    icon = AppIcons.Scanner,
                    color = MaterialTheme.colorScheme.onPrimary,
                    iconSize = AppDimens.Icon.Sm,
                    onClick = onScannerClick
                )
            }
        } else {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(AppDimens.Space.Xxs))

                subTitle?.let {
                    Text(
                        text = subTitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}