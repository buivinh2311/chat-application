package com.example.chatapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapplication.navigation.AppNavHost
import com.example.chatapplication.ui.theme.ChatApplicationTheme
import com.example.feature_chat.screen.ChatDetailScreen
import com.example.feature_chat.screen.ChatScreen
import com.example.feature_friend_request.FriendRequestScreen
import com.example.feature_friend_suggestion.FriendSuggestionScreen
import com.example.feature_settings.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatApplicationTheme {
                AppNavHost()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatApplicationTheme {
//        ChatScreen(
//            onChatItemClick = {},
//            onBottomActionClick = {}
//        )
        ChatDetailScreen {  }
    }
}