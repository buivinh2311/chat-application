package com.example.feature_settings.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.AsyncImage
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.menu.Gender
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var selectedDate by remember {
        mutableStateOf("")
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
        mutableStateOf<Gender?>(null)
    }

    val genders = Gender.entries

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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(
                    vertical = AppDimens.Space.Xl,
                    horizontal = AppDimens.Space.Lg
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box{
                    AsyncImage(
                        model = "",
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.account),
                        error = painterResource(R.drawable.account),
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .clip(shape = CircleShape)
                            .clickable{

                            }
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
                    text = "name",
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
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
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
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
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
                Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
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
                Spacer(modifier = Modifier.height(AppDimens.Space.Md))

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
                Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = selectedDate,
                            onValueChange = { },
                            readOnly = true,
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.hint_date_of_birth),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
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
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
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

    if(showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if(millis != null) {
                            val formatter = SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            )
                            selectedDate = formatter.format(Date(millis))
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

@Composable
fun Gender.displayName(): String {
    return when(this) {
        Gender.MALE -> stringResource(R.string.text_male)
        Gender.FEMALE -> stringResource(R.string.text_female)
        Gender.OTHER -> stringResource(R.string.text_other)
    }
}