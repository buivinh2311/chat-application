package com.example.feature_settings.presentation.screen

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.core_model.enum.BannerType
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_model.enum.Gender
import com.example.core_ui.component.ErrorScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.showToast
import com.example.core_ui.state.UiState
import com.example.core_utils.uriToFile
import com.example.feature_settings.presentation.event.ProfileEvent
import com.example.feature_settings.presentation.viewmodel.ProfileViewModel
import com.example.share_presentation.AppTopBanner
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        profileViewModel.loadCurrentUser()
        profileViewModel.event.collect { event ->
            when (event) {
                is ProfileEvent.ShowError -> {
                    showToast(
                        context,
                        message = context.getString(R.string.text_unknown_error)
                    )
                }

                is ProfileEvent.UpdateSuccessful -> {
                    showToast(
                        context,
                        message = context.getString(R.string.text_update_success)
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    title = stringResource(R.string.title_profile),
                    onBackClick = onBackClick
                )
            },
            bottomBar = {
                AppBottomBar(
                    selectedAction = AppBottomBarAction.SETTINGS,
                    onBottomActionClick = onBottomActionClick
                )
            }
        ) { innerPadding ->
            when (val state = uiState) {
                UiState.Loading -> {
                    LoadingScreen()
                }

                UiState.Error -> {
                    ErrorScreen(title = "ERROR")
                }

                UiState.Empty -> {

                }

                is UiState.Success -> {
                    val data = state.data
                    val user = data.currentUser
                    val isFirstNameBlank = data.isFirstNameBlank
                    val isLastNameBlank = data.isLastNameBlank
                    var firstName by remember {
                        mutableStateOf(user.firstName)
                    }

                    var lastName by remember {
                        mutableStateOf(user.lastName)
                    }

                    var address by remember {
                        mutableStateOf(user.address.orEmpty())
                    }

                    var showDatePicker by remember {
                        mutableStateOf(false)
                    }

                    var selectedDate by remember {
                        mutableStateOf(user.dateOfBirth)
                    }

                    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                    val datePickerState = rememberDatePickerState(
                        selectableDates = object : SelectableDates {
                            override fun isSelectableYear(year: Int): Boolean {
                                return year <= currentYear
                            }

                            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                                return utcTimeMillis <= System.currentTimeMillis()
                            }
                        }
                    )

                    var expanded by remember {
                        mutableStateOf(false)
                    }

                    var selectedGender by remember {
                        mutableStateOf(user.gender)
                    }

                    val genders = Gender.entries
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.PickVisualMedia()
                    ) { uri ->
                        if (uri != null) {
                            val file = uriToFile(context, uri)
                            profileViewModel.uploadAvatar(user.id, file)
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
                            .imePadding()
                            .fillMaxSize(),
                        contentPadding = PaddingValues(
                            vertical = AppDimens.Space.Xl,
                            horizontal = AppDimens.Space.Lg
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Box {
                                AsyncImage(
                                    model = user.avatarUrl,
                                    contentDescription = null,
                                    placeholder = painterResource(R.drawable.account),
                                    error = painterResource(R.drawable.account),
                                    modifier = Modifier
                                        .fillMaxWidth(0.4f)
                                        .aspectRatio(1f)
                                        .clip(shape = CircleShape)
                                        .clickable {
                                            launcher.launch(
                                                PickVisualMediaRequest(
                                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                                )
                                            )
                                        },
                                    contentScale = ContentScale.Crop
                                )

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .offset(
                                            x = AppDimens.Space.Sm,
                                            y = AppDimens.Space.Sm
                                        )
                                        .background(
                                            Color.LightGray,
                                            shape = CircleShape
                                        )
                                        .border(
                                            width = AppDimens.Space.Sm,
                                            color = MaterialTheme.colorScheme.background,
                                            shape = CircleShape
                                        )
                                ) {
                                    Icon(
                                        painter = AppIcons.Camera,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(AppDimens.Space.Lg)
                                            .size(AppDimens.Icon.Md),
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(AppDimens.Space.Sm))

                            Text(
                                text = "${user.firstName} ${user.lastName}",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(AppDimens.Space.Xxl))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
                            ) {
                                OutlinedTextField(
                                    value = firstName,
                                    onValueChange = { firstName = it },
                                    placeholder = {
                                        Text(
                                            text = stringResource(R.string.hint_first_name),
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = 0.6f
                                            )
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    singleLine = true,
                                    modifier = Modifier.weight(1f)
                                )

                                OutlinedTextField(
                                    value = lastName,
                                    onValueChange = { lastName = it },
                                    placeholder = {
                                        Text(
                                            text = stringResource(R.string.hint_last_name),
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = 0.6f
                                            )
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    singleLine = true,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Spacer(modifier = Modifier.height(AppDimens.Space.Xs))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
                            ) {
                                AnimatedVisibility(
                                    visible = isFirstNameBlank || isLastNameBlank,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = if (isFirstNameBlank) stringResource(R.string.text_first_name_blank)
                                        else " ",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.padding(
                                            start = AppDimens.Space.Sm
                                        )
                                    )
                                }

                                AnimatedVisibility(
                                    visible = isLastNameBlank,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = stringResource(R.string.text_last_name_blank),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.padding(
                                            start = AppDimens.Space.Sm
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                            OutlinedTextField(
                                value = user.email,
                                onValueChange = { },
                                readOnly = true,
                                enabled = false,
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

                            OutlinedTextField(
                                value = address,
                                onValueChange = { address = it },
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.hint_address),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(AppDimens.Space.Lg))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    OutlinedTextField(
                                        value = selectedDate?.toDisplayString() ?: "",
                                        onValueChange = { },
                                        readOnly = true,
                                        placeholder = {
                                            Text(
                                                text = stringResource(R.string.hint_date_of_birth),
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = MaterialTheme.colorScheme.onBackground.copy(
                                                    alpha = 0.6f
                                                )
                                            )
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Next
                                        ),
                                        trailingIcon = {
                                            Icon(
                                                painter = AppIcons.Calendar,
                                                contentDescription = null,
                                                modifier = Modifier.size(AppDimens.Icon.Sm)
                                            )
                                        },
                                        singleLine = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )

                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .clickable {
                                                showDatePicker = true
                                            }
                                    )
                                }
                                Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = {
                                        expanded = !expanded
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    OutlinedTextField(
                                        value = selectedGender?.displayName() ?: "",
                                        onValueChange = { },
                                        readOnly = true,
                                        placeholder = {
                                            Text(
                                                text = stringResource(R.string.hint_gender),
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = MaterialTheme.colorScheme.onBackground.copy(
                                                    alpha = 0.6f
                                                )
                                            )
                                        },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .menuAnchor(
                                                type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                                                enabled = true
                                            )
                                    )

                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = {
                                            expanded = false
                                        }
                                    ) {
                                        genders.forEach { gender ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        text = gender.displayName(),
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                },
                                                onClick = {
                                                    selectedGender = gender
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(AppDimens.Space.Xxl))

                            Button(
                                onClick = {
                                    profileViewModel.updateProfile(
                                        user = user,
                                        firstName = firstName,
                                        lastName = lastName,
                                        address = address,
                                        dateOfBirth = selectedDate,
                                        gender = selectedGender
                                    )
                                },
                                shape = RoundedCornerShape(AppDimens.Radius.Sm),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.label_update),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(
                                        vertical = AppDimens.Space.Sm,
                                        horizontal = AppDimens.Space.Xs
                                    )
                                )
                            }
                        }
                    }

                    if (showDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = {
                                showDatePicker = false
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        val millis = datePickerState.selectedDateMillis
                                        if (millis != null) {
                                            val formatter = SimpleDateFormat(
                                                "dd/MM/yyyy",
                                                Locale.getDefault()
                                            )
                                            selectedDate = Timestamp(Date(millis))
                                        }
                                        showDatePicker = false
                                    }
                                ) {
                                    Text(
                                        text = stringResource(R.string.label_confirm),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        showDatePicker = false
                                    }
                                ) {
                                    Text(
                                        text = stringResource(R.string.label_cancel),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        ) {
                            DatePicker(
                                state = datePickerState
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Gender.displayName(): String {
    return when (this) {
        Gender.MALE -> stringResource(R.string.text_male)
        Gender.FEMALE -> stringResource(R.string.text_female)
        Gender.OTHER -> stringResource(R.string.text_other)
    }
}

fun Timestamp.toDisplayString(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(toDate())
}