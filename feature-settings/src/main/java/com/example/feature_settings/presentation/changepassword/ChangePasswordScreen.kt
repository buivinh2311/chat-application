package com.example.feature_settings.presentation.changepassword

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction

@Composable
fun ChangePasswordScreen(
    onBack: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    var oldPassword by remember {
        mutableStateOf("")
    }

    var newPassword by remember {
        mutableStateOf("")
    }

    var repeatPassword by remember {
        mutableStateOf("")
    }

    var isShowOldPassword by remember {
        mutableStateOf(false)
    }

    var isShowNewPassword by remember {
        mutableStateOf(false)
    }

    var isShowRepeatPassword by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_password),
                onBack = onBack
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(
                    vertical = AppDimens.Space.Xl,
                    horizontal = AppDimens.Space.Lg
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.hint_old_password),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    visualTransformation = if(isShowOldPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                isShowOldPassword = !isShowOldPassword
                            }
                        ) {
                            Icon(
                                painter = if(isShowOldPassword) {
                                    AppIcons.HidePassword
                                } else {
                                    AppIcons.ShowPassword
                                },
                                contentDescription = null,
                                modifier = Modifier.size(AppDimens.Icon.Sm)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.hint_new_password),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    visualTransformation = if(isShowNewPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                isShowNewPassword = !isShowNewPassword
                            }
                        ) {
                            Icon(
                                painter = if(isShowNewPassword) {
                                    AppIcons.HidePassword
                                } else {
                                    AppIcons.ShowPassword
                                },
                                contentDescription = null,
                                modifier = Modifier.size(AppDimens.Icon.Sm)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                OutlinedTextField(
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.hint_repeat_password),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    visualTransformation = if(isShowRepeatPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                isShowRepeatPassword = !isShowRepeatPassword
                            }
                        ) {
                            Icon(
                                painter = if(isShowRepeatPassword) {
                                    AppIcons.HidePassword
                                } else {
                                    AppIcons.ShowPassword
                                },
                                contentDescription = null,
                                modifier = Modifier.size(AppDimens.Icon.Sm)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Xxl))

                Button(
                    onClick = {},
                    shape = RoundedCornerShape(AppDimens.Radius.Sm),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.label_update),
                        style  = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(
                            vertical = AppDimens.Space.Sm,
                            horizontal = AppDimens.Space.Xs
                        )
                    )
                }
            }
        }
    }
}