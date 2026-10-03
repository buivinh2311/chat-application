package com.example.feature_auth.presentation.register

import androidx.compose.runtime.Composable

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onHasAccountClick: () -> Unit
) {
//    var firstName by remember {
//        mutableStateOf("")
//    }
//
//    var lastName by remember {
//        mutableStateOf("")
//    }
//
//    var email by remember {
//        mutableStateOf("")
//    }
//
//    var password by remember {
//        mutableStateOf("")
//    }
//
//    var repeatPassword by remember {
//        mutableStateOf("")
//    }
//
//    var isShowPassword by remember {
//        mutableStateOf(false)
//    }
//
//    var isShowRepeatPassword by remember {
//        mutableStateOf(false)
//    }
//
//    var showBanner by remember {
//        mutableStateOf(false)
//    }
//
//    val registerViewModel: RegisterViewModel = hiltViewModel()
//    val uiState by registerViewModel.uiState.collectAsStateWithLifecycle()
//
//    LaunchedEffect(Unit) {
//        registerViewModel.event.collect { event ->
//            when (event) {
//                RegisterEvent.NavigateToLogin -> onNavigateToLogin()
//                is RegisterEvent.ShowError -> {
//                    showBanner = true
//                    delay(3000)
//                    showBanner = false
//                }
//            }
//        }
//    }
//
//    if (uiState.isLoading) {
//        LoadingScreen()
//    } else {
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            LazyColumn(
//                modifier = Modifier
//                    .statusBarsPadding()
//                    .navigationBarsPadding()
//                    .imePadding()
//                    .fillMaxSize()
//                    .padding(
//                        vertical = AppDimens.Space.Xl,
//                        horizontal = AppDimens.Space.Lg
//                    )
//            ) {
//                item {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.account),
//                            contentDescription = null,
//                            modifier = Modifier.fillMaxWidth(0.5f)
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
//
//                    Text(
//                        text = stringResource(R.string.title_register),
//                        style = MaterialTheme.typography.headlineLarge,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                    Spacer(modifier = Modifier.height(AppDimens.Space.Lg))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
//                    ) {
//                        OutlinedTextField(
//                            value = firstName,
//                            onValueChange = { firstName = it },
//                            placeholder = {
//                                Text(
//                                    text = stringResource(R.string.hint_first_name),
//                                    style = MaterialTheme.typography.bodyLarge,
//                                    color = if (uiState.isFirstNameBlank) MaterialTheme.colorScheme.error
//                                    else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
//                                )
//                            },
//                            keyboardOptions = KeyboardOptions(
//                                keyboardType = KeyboardType.Text,
//                                imeAction = ImeAction.Next
//                            ),
//                            singleLine = true,
//                            isError = uiState.isFirstNameBlank,
//                            modifier = Modifier.weight(1f)
//                        )
//
//                        OutlinedTextField(
//                            value = lastName,
//                            onValueChange = { lastName = it },
//                            placeholder = {
//                                Text(
//                                    text = stringResource(R.string.hint_last_name),
//                                    style = MaterialTheme.typography.bodyLarge,
//                                    color = if (uiState.isLastNameBlank) MaterialTheme.colorScheme.error
//                                    else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
//                                )
//                            },
//                            keyboardOptions = KeyboardOptions(
//                                keyboardType = KeyboardType.Text,
//                                imeAction = ImeAction.Next
//                            ),
//                            singleLine = true,
//                            isError = uiState.isLastNameBlank,
//                            modifier = Modifier.weight(1f)
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
//                    ) {
//                        AnimatedVisibility(
//                            visible = uiState.isFirstNameBlank || uiState.isLastNameBlank,
//                            modifier = Modifier.weight(1f)
//                        ) {
//                            Text(
//                                text = if (uiState.isFirstNameBlank) stringResource(R.string.text_first_name_blank)
//                                else " ",
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.error,
//                                modifier = Modifier.padding(
//                                    start = AppDimens.Space.Sm
//                                )
//                            )
//                        }
//
//                        AnimatedVisibility(
//                            visible = uiState.isLastNameBlank,
//                            modifier = Modifier.weight(1f)
//                        ) {
//                            Text(
//                                text = stringResource(R.string.text_last_name_blank),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.error,
//                                modifier = Modifier.padding(
//                                    start = AppDimens.Space.Sm
//                                )
//                            )
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(AppDimens.Space.Md))
//
//                    OutlinedTextField(
//                        value = email,
//                        onValueChange = { email = it },
//                        placeholder = {
//                            Text(
//                                text = stringResource(R.string.hint_email),
//                                style = MaterialTheme.typography.bodyLarge,
//                                color = if (uiState.isEmailBlank || uiState.isEmailTypeError) MaterialTheme.colorScheme.error
//                                else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
//                            )
//                        },
//                        keyboardOptions = KeyboardOptions(
//                            keyboardType = KeyboardType.Email,
//                            imeAction = ImeAction.Next
//                        ),
//                        singleLine = true,
//                        isError = uiState.isEmailTypeError || uiState.isEmailBlank,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                    Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
//
//                    AnimatedVisibility(
//                        visible = uiState.isEmailTypeError || uiState.isEmailBlank
//                    ) {
//                        if (uiState.isEmailBlank) {
//                            Text(
//                                text = stringResource(R.string.text_email_blank),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.error,
//                                modifier = Modifier.padding(
//                                    start = AppDimens.Space.Sm
//                                )
//                            )
//                        } else if (uiState.isEmailTypeError) {
//                            Text(
//                                text = stringResource(R.string.text_email_type_error),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.error,
//                                modifier = Modifier.padding(
//                                    start = AppDimens.Space.Sm
//                                )
//                            )
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(AppDimens.Space.Md))
//
//                    OutlinedTextField(
//                        value = password,
//                        onValueChange = { password = it },
//                        placeholder = {
//                            Text(
//                                text = stringResource(R.string.hint_password),
//                                style = MaterialTheme.typography.bodyLarge,
//                                color = if (uiState.isPasswordBlank || uiState.isNotStrongPassword) MaterialTheme.colorScheme.error
//                                else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
//                            )
//                        },
//                        keyboardOptions = KeyboardOptions(
//                            keyboardType = KeyboardType.Password,
//                            imeAction = ImeAction.Next
//                        ),
//                        singleLine = true,
//                        isError = uiState.isPasswordBlank || uiState.isNotStrongPassword,
//                        visualTransformation = if (isShowPassword) {
//                            VisualTransformation.None
//                        } else {
//                            PasswordVisualTransformation()
//                        },
//                        trailingIcon = {
//                            IconButton(
//                                onClick = {
//                                    isShowPassword = !isShowPassword
//                                }
//                            ) {
//                                Icon(
//                                    painter = if (isShowPassword) {
//                                        AppIcons.HidePassword
//                                    } else {
//                                        AppIcons.ShowPassword
//                                    },
//                                    contentDescription = null,
//                                    modifier = Modifier.size(AppDimens.Icon.Sm)
//                                )
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                    Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
//
//                    AnimatedVisibility(
//                        visible = uiState.isPasswordBlank || uiState.isNotStrongPassword
//                    ) {
//                        if (uiState.isPasswordBlank) {
//                            Text(
//                                text = stringResource(R.string.text_password_blank),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.error,
//                                modifier = Modifier.padding(
//                                    start = AppDimens.Space.Sm
//                                )
//                            )
//                        } else if (uiState.isNotStrongPassword) {
//                            Text(
//                                text = stringResource(R.string.text_not_strong_password),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.error,
//                                modifier = Modifier.padding(
//                                    start = AppDimens.Space.Sm
//                                )
//                            )
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(AppDimens.Space.Md))
//
//                    OutlinedTextField(
//                        value = repeatPassword,
//                        onValueChange = { repeatPassword = it },
//                        placeholder = {
//                            Text(
//                                text = stringResource(R.string.hint_repeat_password),
//                                style = MaterialTheme.typography.bodyLarge,
//                                color = if (uiState.isRepeatPasswordBlank || uiState.isRepeatPasswordError) MaterialTheme.colorScheme.error
//                                else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
//                            )
//                        },
//                        keyboardOptions = KeyboardOptions(
//                            keyboardType = KeyboardType.Password,
//                            imeAction = ImeAction.Done
//                        ),
//                        singleLine = true,
//                        isError = uiState.isRepeatPasswordBlank || uiState.isRepeatPasswordError,
//                        visualTransformation = if (isShowRepeatPassword) {
//                            VisualTransformation.None
//                        } else {
//                            PasswordVisualTransformation()
//                        },
//                        trailingIcon = {
//                            IconButton(
//                                onClick = {
//                                    isShowRepeatPassword = !isShowRepeatPassword
//                                }
//                            ) {
//                                Icon(
//                                    painter = if (isShowRepeatPassword) {
//                                        AppIcons.HidePassword
//                                    } else {
//                                        AppIcons.ShowPassword
//                                    },
//                                    contentDescription = null,
//                                    modifier = Modifier.size(AppDimens.Icon.Sm)
//                                )
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                    Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
//
//                    AnimatedVisibility(
//                        visible = uiState.isRepeatPasswordBlank || uiState.isRepeatPasswordError
//                    ) {
//                        if (uiState.isRepeatPasswordBlank) {
//                            Text(
//                                text = stringResource(R.string.text_repeat_password_blank),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.error,
//                                modifier = Modifier.padding(
//                                    start = AppDimens.Space.Sm
//                                )
//                            )
//                        } else if (uiState.isRepeatPasswordError) {
//                            Text(
//                                text = stringResource(R.string.text_repeat_password_error),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.error,
//                                modifier = Modifier.padding(
//                                    start = AppDimens.Space.Sm
//                                )
//                            )
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(AppDimens.Space.Md))
//
//                    Button(
//                        onClick = {
//                            registerViewModel
//                                .register(
//                                    firstName,
//                                    lastName,
//                                    email,
//                                    password,
//                                    repeatPassword
//                                )
//                        },
//                        shape = RoundedCornerShape(AppDimens.Radius.Sm),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = MaterialTheme.colorScheme.primary
//                        ),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(56.dp)
//                    ) {
//                        Text(
//                            text = stringResource(R.string.label_register),
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                    }
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = stringResource(R.string.label_had_account),
//                            style = MaterialTheme.typography.titleMedium
//                        )
//
//                        TextButton(
//                            onClick = onHasAccountClick
//                        ) {
//                            Text(
//                                text = stringResource(R.string.label_login),
//                                style = MaterialTheme.typography.titleMedium,
//                                color = MaterialTheme.colorScheme.primary
//                            )
//                        }
//                    }
//                }
//            }
//
//            if (showBanner) {
//                AppTopBanner(
//                    modifier = Modifier
//                        .align(Alignment.TopCenter)
//                        .zIndex(1f),
//                    title = when (uiState.error) {
//                        RegisterResult.Failure.EmailAlreadyExists -> {
//                            stringResource(R.string.text_email_already_exists)
//                        }
//
//                        RegisterResult.Failure.InvalidEmail -> {
//                            stringResource(R.string.text_invalid_email)
//                        }
//
//                        RegisterResult.Failure.WeakPassword -> {
//                            stringResource(R.string.text_weak_password)
//                        }
//
//                        RegisterResult.Failure.TooManyRequests -> {
//                            stringResource(R.string.text_too_many_requests)
//                        }
//
//                        RegisterResult.Failure.Network -> {
//                            stringResource(R.string.text_network_error)
//                        }
//
//                        RegisterResult.Failure.Unknown -> {
//                            stringResource(R.string.text_unknown_error)
//                        }
//
//                        else -> {
//                            ""
//                        }
//                    }
//                )
//            }
//        }
//    }
}