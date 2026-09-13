package com.example.feature_settings.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.enum.DarkModeState
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.ErrorScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.feature_settings.presentation.menu.DarkModeOptionItem
import com.example.feature_settings.presentation.viewmodel.DarkModeViewModel

@Composable
fun DarkModeScreen(
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val darkModeViewModel: DarkModeViewModel = hiltViewModel()
    val uiState by darkModeViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        darkModeViewModel.observerThemeState()
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
        when(val state = uiState) {
            UiState.Loading -> {
                LoadingScreen()
            }

            UiState.Error -> {
                ErrorScreen(title = "ERROR")
            }

            UiState.Empty -> {

            }

            is UiState.Success -> {
                val darkModeState = state.data
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
                                    darkModeViewModel.toggleThemeState(item.darkModeState)
                                }
                                .padding(AppDimens.Space.Lg),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = darkModeState == item.darkModeState,
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
    }
}

@Composable
fun darkModeOptionItems(): List<DarkModeOptionItem> {
    return listOf(
        DarkModeOptionItem(
            title = stringResource(R.string.title_off),
            darkModeState = DarkModeState.OFF
        ),

        DarkModeOptionItem(
            title = stringResource(R.string.title_on),
            darkModeState = DarkModeState.ON
        ),

        DarkModeOptionItem(
            title = stringResource(R.string.title_default),
            darkModeState = DarkModeState.DEFAULT
        )
    )
}