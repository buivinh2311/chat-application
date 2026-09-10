package com.example.feature_chat.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_model.User
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppIconButton
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_chat.presentation.component.ConversationStatus
import com.example.feature_chat.presentation.component.PickGroupMemberItem
import com.example.share_presentation.ConversationItem

@Composable
fun PickGroupMemberScreen(
    userId: String,
    onBackClick: () -> Unit
) {
    var memberQuantity by remember {
        mutableStateOf(1)
    }

    var groupName by remember {
        mutableStateOf("")
    }

    var keyword by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_pick_group_member),
                subTitle = stringResource(R.string.title_quantity_choose, memberQuantity),
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            contentPadding = PaddingValues(
                vertical = AppDimens.Space.Md
            )
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.Space.Lg),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppIconButton(
                        icon = AppIcons.CameraFilled,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        onClick = {

                        }
                    )
                    Spacer(modifier = Modifier.width(AppDimens.Space.Lg))

                    BasicTextField(
                        value = groupName,
                        onValueChange = { groupName = it },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(AppDimens.Space.Xxl)
                            .clip(RoundedCornerShape(AppDimens.Radius.Sm))
                            .background(
                                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f)
                            )
                            .padding(horizontal = AppDimens.Space.Lg),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (groupName.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.hint_group_name),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onBackground.copy(
                                            alpha = 0.5f
                                        )
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(AppDimens.Space.Lg))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppDimens.Space.Xxl)
                        .padding(horizontal = AppDimens.Space.Lg)
                        .clip(RoundedCornerShape(AppDimens.Radius.Sm))
                        .background(MaterialTheme.colorScheme.onBackground.copy(0.05f))
                        .padding(AppDimens.Space.Md),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = AppIcons.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )

                    Spacer(modifier = Modifier.width(AppDimens.Space.Sm))
                    BasicTextField(
                        value = keyword,
                        onValueChange = {
                            keyword = it
//                                onQueryChange(it)
                        },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        cursorBrush = SolidColor(
                            MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusRequester),
                        decorationBox = { innerTextField ->
                            Box {
                                if (keyword.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.hint_query),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(AppDimens.Space.Lg))
            }

            items(
                count = 12
            ) {
                PickGroupMemberItem(
                    name = "haha",
                    onCheckedChange = {},
                    isChecked = true
                )
            }
        }
    }
}