package com.example.feature_contacts.presentation.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.ErrorScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.core_utils.isAtBottom
import com.example.core_utils.isScrolled
import com.example.feature_contacts.presentation.contacts.component.ContactsCategory
import com.example.feature_contacts.presentation.contacts.component.ContactsFriendRequest
import com.example.feature_contacts.presentation.contacts.component.ContactsFriendSent
import com.example.feature_contacts.presentation.contacts.component.ContactsFriendSuggest
import com.example.feature_contacts.presentation.contacts.component.ContactsItem
import com.example.feature_contacts.presentation.contacts.component.ContactsSearchBar
import com.example.feature_contacts.presentation.contacts.ContactsViewModel

@Composable
fun ContactsScreen(
    onSearchClick: () -> Unit,
    onViewAllClick: () -> Unit,
    onViewMoreClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val contactsViewModel: ContactsViewModel = hiltViewModel()
    val uiState by contactsViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        contactsViewModel.loadEverybody()
    }

    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf { listState.isScrolled }
    }
    val isAtBottom by remember {
        derivedStateOf { listState.isAtBottom }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_everybody),
                isScrolled = isScrolled
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedAction = AppBottomBarAction.CONTACTS,
                showDivider = !isAtBottom,
                onBottomActionClick = onBottomActionClick
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            UiState.Loading -> {
                LoadingScreen()
            }

            UiState.Error -> {
                ErrorScreen(message = "ERROR")
            }

            UiState.Empty -> {

            }

            is UiState.Success -> {
                val data = state.data
                val friendRequestReceived = data.friendRequestReceived
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        ContactsSearchBar()
                        Spacer(modifier = Modifier.height(AppDimens.Space.Sm))
                    }

                    LazyColumn(
                        contentPadding = PaddingValues(
                            bottom = AppDimens.Space.Xl
                        ),
                        state = listState
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                            ContactsFriendRequest(
                                onClick = {}
                            )
                            Spacer(
                                modifier = Modifier.height(
                                    AppDimens.Space.Xs + AppDimens.Space.Xxs
                                )
                            )

                            ContactsFriendSent(
                                onClick = {}
                            )
                            Spacer(
                                modifier = Modifier.height(
                                    AppDimens.Space.Xs + AppDimens.Space.Xxs
                                )
                            )

                            ContactsFriendSuggest(
                                onClick = {}
                            )
                            Spacer(modifier = Modifier.height(AppDimens.Space.Lg))
                        }

                        stickyHeader {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                ContactsCategory()
                                Spacer(modifier = Modifier.height(AppDimens.Space.Sm))
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                            Text(
                                text = "H",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .padding(horizontal = AppDimens.Space.Md)
                            )
                            Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                        }

                        items(
                            count = 20
                        ) {
                            ContactsItem(
                                onClick = { }
                            )
                            Spacer(
                                modifier = Modifier.height(
                                    AppDimens.Space.Xs + AppDimens.Space.Xxs
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}