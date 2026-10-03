package com.example.feature_conversation.presentation.conversationoption.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.core_resource.R

@Composable
fun ChangeGroupNameDialog (
    onDismiss: () -> Unit,
    onChange: (String) -> Unit
) {
    var groupName by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.title_group_name),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            OutlinedTextField(
                value = groupName,
                onValueChange = {
                    groupName = it
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.hint_group_name),
                        color = Color.Black.copy(alpha = 0.3f)
                    )
                },
                isError = groupName.isNotBlank() && groupName.trim().isEmpty()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val name = groupName.trim()
                    if (name.isNotEmpty()) {
                        onChange(name)
                    }
                },
                enabled = groupName.trim().isNotEmpty()
            ) {
                Text(
                    text = stringResource(R.string.action_confirm),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(R.string.action_cancel),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    )
}