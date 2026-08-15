package com.example.feature_everybody.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction
import com.example.share_presentation.FriendRequest
import com.example.share_presentation.FriendSuggestion
import kotlin.math.min

@Composable
fun EverybodyScreen(
    onSearchClick: () -> Unit,
    onViewAllClick: () -> Unit,
    onViewMoreClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val everyBodyViewModel: EveryBodyViewModel = hiltViewModel()
    val friendReceivedRequest by everyBodyViewModel.friendReceivedRequest
        .collectAsStateWithLifecycle(emptyList())

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_everybody),
                onSearchClick = onSearchClick
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedAction = AppBottomBarAction.EVERYBODY,
                onBottomActionClick = onBottomActionClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                vertical = AppDimens.Space.Lg
            )
        ) {
            if(friendReceivedRequest.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = AppDimens.Space.Lg
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.title_friend_request),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )

                        TextButton(
                            onClick = onViewAllClick
                        ) {
                            Text(
                                text = stringResource(R.string.label_view_all),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                items(
                    count = min(5, friendReceivedRequest.size),
                    key = { index -> friendReceivedRequest[index].request.id }
                ) { index ->
                    FriendRequest(
                        user = friendReceivedRequest[index].user,
                        request = friendReceivedRequest[index].request,
                        onAcceptClick = { request ->
                            everyBodyViewModel.acceptRequest(request)
                        },
                        onRejectClick = { request ->
                            everyBodyViewModel.rejectRequest(request.id)
                        },
                        onClick = { }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = AppDimens.Space.Lg
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.title_friend_suggetion),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )

                    TextButton(
                        onClick = onViewMoreClick
                    ) {
                        Text(
                            text = stringResource(R.string.label_view_more),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            items(
                count = 5
            ) {
                FriendSuggestion(
                    onClick = { }
                )
            }
        }
    }
}