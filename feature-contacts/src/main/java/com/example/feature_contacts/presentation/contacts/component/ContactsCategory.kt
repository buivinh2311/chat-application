package com.example.feature_contacts.presentation.contacts.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.core_resource.AppDimens
import com.example.core_resource.R

@Composable
fun ContactsCategory(

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.Space.Md)
    ) {
        Text(
            text = stringResource(R.string.label_friend),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(AppDimens.Space.Lg))
                .clickable {

                }
                .background(MaterialTheme.colorScheme.primary)
                .padding(
                    vertical = AppDimens.Space.Sm,
                    horizontal = AppDimens.Space.Lg
                )
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

        Text(
            text = stringResource(R.string.label_group),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(AppDimens.Space.Lg))
                .clickable {

                }
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(
                    vertical = AppDimens.Space.Sm,
                    horizontal = AppDimens.Space.Lg
                )
        )
    }
}