package com.example.feature_chat.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core_ui.component.Message
import com.example.feature_chat.component.ChatDetailBottomBar
import com.example.feature_chat.component.ChatDetailTopBar

@Composable
fun ChatDetailScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ChatDetailTopBar(
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            ChatDetailBottomBar()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(
                count = 12
            ) { index ->
                if(index % 2 == 0) {
                    Message(
                        content = "message",
                        isMine = false
                    )
                } else {
                    Message(
                        content = "message",
                        isMine = true
                    )
                }
            }
        }
    }
}