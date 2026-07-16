package com.example.feature_settings.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.unit.dp
import com.example.core_model.DarkModeStatus
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.menu.DarkModeOptionItem

@Composable
fun DarkModeScreen(
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    var selectedDarkModeStatus by remember {
        mutableStateOf(DarkModeStatus.DEFAULT)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_dark_mode),
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(
                    vertical = AppDimens.Space.Md
                )
        ) {
            darkModeOptionItems().forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedDarkModeStatus = item.darkModeStatus
                        }
                        .padding(AppDimens.Space.Lg),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedDarkModeStatus == item.darkModeStatus,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(modifier = Modifier.height(AppDimens.Space.Xs))

            Text(
                text = stringResource(R.string.text_dark_mode_default),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    horizontal = AppDimens.Space.Lg
                )
            )
        }
    }
}

@Composable
fun darkModeOptionItems(): List<DarkModeOptionItem> {
    return listOf(
        DarkModeOptionItem(
            title = stringResource(R.string.title_off),
            darkModeStatus = DarkModeStatus.OFF
        ),

        DarkModeOptionItem(
            title = stringResource(R.string.title_on),
            darkModeStatus = DarkModeStatus.ON
        ),

        DarkModeOptionItem(
            title = stringResource(R.string.title_default),
            darkModeStatus = DarkModeStatus.DEFAULT
        )
    )
}