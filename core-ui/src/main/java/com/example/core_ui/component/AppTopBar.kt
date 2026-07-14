package com.example.core_ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onSearchClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                start = AppDimens.Space.Lg,
                end = AppDimens.Space.Lg,
                top = AppDimens.Space.Md,
                bottom = AppDimens.Space.Sm
            )
    ) {
        if(onBackClick != null) {
            AppIconButton(
                icon = AppIcons.Back,
                color = MaterialTheme.colorScheme.onBackground,
                onClick = onBackClick
            )
        }
        Spacer(modifier = Modifier.width(AppDimens.Space.Lg))

        Text(
            text = title,
            style = if(onBackClick != null) MaterialTheme.typography.titleLarge
            else MaterialTheme.typography.headlineSmall,
        )

        if(onSearchClick != null) {
            AppIconButton(
                icon = AppIcons.Search,
                color = MaterialTheme.colorScheme.onBackground,
                onClick = onSearchClick
            )
        }
    }
}