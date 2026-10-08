package com.example.feature_conversation.presentation.creategroup

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.User
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.ErrorScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.showToast
import com.example.core_ui.state.UiState
import com.example.core_utils.isScrolled
import com.example.core_utils.uriToFile
import com.example.feature_conversation.presentation.creategroup.component.CreateGroupInfo
import com.example.feature_conversation.presentation.creategroup.component.PickGroupMemberBottomBar
import com.example.feature_conversation.presentation.creategroup.component.PickGroupMemberItem
import com.example.feature_conversation.presentation.creategroup.component.PickGroupMemberSearchBar

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun CreateGroupScreen(
    currentFriendId: String,
    onNavigationToConversationDetail: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var groupMember: List<User> by remember {
        mutableStateOf(emptyList())
    }

    var groupName by remember {
        mutableStateOf("")
    }

    var selectedAvatarUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val createGroupViewModel: CreateGroupViewModel = hiltViewModel()
    val uiState by createGroupViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        createGroupViewModel.loadFriends(currentFriendId)
        createGroupViewModel.event.collect { event ->
            when (event) {
                CreateGroupEvent.NotEnough -> {
                    showToast(
                        context = context,
                        message = context.getString(R.string.text_not_enough_member)
                    )
                }

                CreateGroupEvent.ShowError -> {
                    showToast(
                        context = context,
                        message = context.getString(R.string.text_unknown_error)
                    )
                }

                is CreateGroupEvent.NavigateToConversationDetail -> {
                    val conversationId = event.conversationId
                    onNavigationToConversationDetail(conversationId)
                }
            }
        }
    }

    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf { listState.isScrolled }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_create_group),
                subTitle = stringResource(
                    R.string.title_quantity_choose, groupMember.size
                ),
                isScrolled = isScrolled,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            UiState.Loading -> {
                LoadingScreen()
            }

            UiState.Error -> {
                ErrorScreen(title = "error")
            }

            UiState.Empty -> {

            }

            is UiState.Success -> {
                val data = state.data
                groupMember = data.groupMember
                val friends = data.friends
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(
                            vertical = AppDimens.Space.Lg
                        )
                    ) {
                        item {
                            CreateGroupInfo(
                                selectedAvatarUri = selectedAvatarUri?.toString(),
                                groupName = groupName,
                                isGroupNameBlank = data.isGroupNameBlank,
                                onSelectAvatar = { uri ->
                                    selectedAvatarUri = uri
                                },
                                onGroupNameChange =  { name ->
                                    groupName = name
                                }
                            )

                            PickGroupMemberSearchBar(
                                onSearch = { keyword ->
                                    createGroupViewModel.searchFriend(keyword, friends)
                                }
                            )
                            Spacer(modifier = Modifier.height(AppDimens.Space.Sm))
                        }

                        items(
                            count = friends.size,
                            key = { index -> friends[index].id }
                        ) { index ->
                            val friend = friends[index]
                            val isChecked = groupMember.any { it.id == friend.id }
                            PickGroupMemberItem(
                                name = friend.fullName,
                                avatarUrl = friend.avatarUrl,
                                onCheckedChange = {
                                    if(isChecked) {
                                        createGroupViewModel.removeGroupMember(friend)
                                    } else {
                                        createGroupViewModel.addGroupMember(friend)
                                    }
                                },
                                checked = isChecked
                            )
                        }
                    }

                    PickGroupMemberBottomBar(
                        groupMember = groupMember,
                        onRemoveMember = { member ->
                            createGroupViewModel.removeGroupMember(member)
                        },
                        onCreateGroup = {
                            createGroupViewModel.createGroup(
                                name = groupName,
                                avatar = selectedAvatarUri?.let { uriToFile(context, it) },
                                otherUser = groupMember
                            )
                        }
                    )
                }
            }
        }
    }
}