package com.example.feature_auth.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.result.ResendResult
import com.example.core_model.result.ResetPasswordResult
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.feature_auth.presentation.viewmodel.ForgotPasswordViewModel
import kotlinx.coroutines.delay

@Composable
fun ForgotPasswordScreen(
    onNavigateToLogin: () -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }

    var remainingSeconds by rememberSaveable {
        mutableIntStateOf(0)
    }

    val forgotPasswordVViewModel: ForgotPasswordViewModel = hiltViewModel()
    val uiState by forgotPasswordVViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(remainingSeconds) {
        while (remainingSeconds > 0) {
            delay(1000)
            remainingSeconds--
        }
    }

    LazyColumn(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .fillMaxSize()
            .padding(
                vertical = AppDimens.Space.Xl,
                horizontal = AppDimens.Space.Lg
            )
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.account),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
            }
            Spacer(modifier = Modifier.height(AppDimens.Space.Xl))

            Text(
                text = stringResource(R.string.title_forgot_password),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(AppDimens.Space.Lg))

            Text(
                text = stringResource(R.string.text_enter_email),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.height(AppDimens.Space.Lg))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.hint_email),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(AppDimens.Space.Lg))

            AnimatedVisibility(
                visible = uiState.isEmailBlank
            ) {
                Text(
                    text = stringResource(R.string.text_email_blank),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(
                        start = AppDimens.Space.Sm
                    )
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Md))
            }

            if(uiState.sendEmailResult == ResetPasswordResult.Success) {
                if(remainingSeconds > 0) {
                    Text(
                        text = stringResource(R.string.text_sent_reset_password_email, remainingSeconds),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(AppDimens.Space.Lg))
                }
            } else if(uiState.sendEmailResult != null) {
                Text(
                    text = stringResource(R.string.text_unknown_error),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Md))
            }

            Button(
                onClick = {
                    forgotPasswordVViewModel.sendPasswordResetEmail(email)
                    remainingSeconds = 60
                },
                enabled = remainingSeconds <= 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimens.Layout.ButtonHeight),
                shape = RoundedCornerShape(AppDimens.Radius.Sm),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.LightGray
                )
            ) {
                Text(
                    text = stringResource(R.string.label_reset_password),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = AppDimens.Space.Xs)
                )
            }
            Spacer(modifier = Modifier.height(AppDimens.Space.Xs))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        onNavigateToLogin()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.label_back_to_login),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}