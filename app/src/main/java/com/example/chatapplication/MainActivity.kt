package com.example.chatapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapplication.navigation.AppNavHost
import com.example.core_resource.ui.theme.ChatApplicationTheme
import com.example.feature_chat.screen.ChatDetailScreen
import com.example.feature_everybody.EverybodyScreen
import com.example.feature_login.LoginScreen
import com.example.feature_register.RegisterScreen
import com.example.feature_settings.screen.ChangePasswordScreen
import com.example.feature_settings.screen.DarkModeScreen
import com.example.feature_settings.screen.LanguageScreen
import com.example.feature_settings.screen.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost()
                }
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
//        ChatDetailScreen {  }
        EverybodyScreen(
            onBottomActionClick = {},
            onViewAllClick = {},
            onViewMoreClick = { }
        )
//        LoginScreen()
//        RegisterScreen(
//            onHasAccountClick = {}
//        )
//        DarkModeScreen(
//            onBackClick = {},
//            onBottomActionClick = { }
//        )
//        LanguageScreen(
//            onBottomActionClick = {},
//            onBackClick = {}
//        )
//        ProfileScreen(
//            onBackClick = {},
//            onBottomActionClick = {}
//        )
//        ChangePasswordScreen(
//            onBackClick = {}
//        ) { }
    }
}