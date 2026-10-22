package com.example.chatapplication

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chatapplication.navigation.AppNavHost
import com.example.chatapplication.navigation.AppRoute
import com.example.core_model.enum.DarkModeState
import com.example.core_resource.LocalAppDimens
import com.example.core_resource.dimens
import com.example.core_resource.ui.theme.ChatApplicationTheme
import com.example.core_ui.state.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }
        enableEdgeToEdge()
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        )
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val themeState by mainViewModel.themeState.collectAsStateWithLifecycle()
            val startupState by mainViewModel.startupState.collectAsStateWithLifecycle()
            splash.setKeepOnScreenCondition {
                startupState is StartupState.Loading
            }
            when (val state = themeState) {
                is UiState.Success -> {
                    val darkTheme = when (state.data) {
                        DarkModeState.OFF -> false
                        DarkModeState.ON -> true
                    }
                    ChatApplicationTheme(
                        themeState = state.data
                    ) {
                        CompositionLocalProvider(
                            LocalAppDimens provides dimens()
                        ) {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                when (startupState) {
                                    StartupState.Loading -> {}

                                    StartupState.Login -> {
                                        AppNavHost(
                                            startDestination = "login?showDialog=false",
                                            darkTheme = darkTheme
                                        )
                                    }

                                    StartupState.Chat -> {
                                        AppNavHost(
                                            startDestination = AppRoute.CONVERSATION,
                                            darkTheme = darkTheme
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}