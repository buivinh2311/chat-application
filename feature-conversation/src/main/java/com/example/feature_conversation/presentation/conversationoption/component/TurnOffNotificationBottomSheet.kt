package com.example.feature_conversation.presentation.conversationoption.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core_resource.AppDimens
import com.example.core_resource.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TurnOffNotificationBottomSheet(
    onDismissRequest: () -> Unit
) {
    val times = listOf(1, 4, 8, 12, 24)
    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        Column {
            Text(
                text = stringResource(R.string.title_turn_off_message_notification),
                style = MaterialTheme.typography.titleMedium
            )
            HorizontalDivider(
                thickness = AppDimens.Divider.Thin,
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            times.forEach { time ->
                Text(
                    text = stringResource(R.string.text_turn_off_in, time),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(AppDimens.Space.Lg)
                )
                HorizontalDivider(
                    thickness = AppDimens.Divider.Thin,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Text(
                text = stringResource(R.string.text_turn_off_until_turned_back_on),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .padding(AppDimens.Space.Lg)
            )
            HorizontalDivider(
                thickness = AppDimens.Divider.Thin,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}