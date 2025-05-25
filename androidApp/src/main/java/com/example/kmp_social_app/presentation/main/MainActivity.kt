package com.example.kmp_social_app.presentation.main


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ru.sobeninalex.resources.MainAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        var uiState: MainUiState by mutableStateOf(MainUiState.Loading)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    uiState = it
                }
            }
        }

        splash.setKeepOnScreenCondition {
            uiState == MainUiState.Loading
        }

        setContent {
            MainAppTheme {
                when (val state = uiState) {
                    is MainUiState.Loading -> Unit
                    is MainUiState.Success -> {
                        MainNavigationGraph(uiState = state)
                    }
                }
            }
        }
    }
}
