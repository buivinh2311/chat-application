package com.example.feature_settings.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import com.example.core_model.DarkModeStatus
import com.example.core_model.Language
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.menu.DarkModeOptionItem
import com.example.feature_settings.menu.LanguageOptionItem

@Composable
fun LanguageScreen(
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    var selectedLanguage by remember {
        mutableStateOf(Language.ENGLISH)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_language),
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
                .padding(
                    vertical = AppDimens.Space.Md
                )
        ) {
            item {
                languageOptionItems().forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedLanguage = item.language
                            }
                            .padding(AppDimens.Space.Lg),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedLanguage == item.language,
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
            }
        }
    }
}

@Composable
fun languageOptionItems(): List<LanguageOptionItem> {
    return listOf(
        LanguageOptionItem(
            title = stringResource(R.string.title_english),
            language = Language.ENGLISH
        ),

        LanguageOptionItem(
            title = stringResource(R.string.title_vietnamese),
            language = Language.VIETNAMESE
        )
    )
}