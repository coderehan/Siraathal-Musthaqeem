package com.islamicapp.siraathalmusthaqeem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.islamicapp.siraathalmusthaqeem.screen.navigation.Navigation
import com.islamicapp.siraathalmusthaqeem.screen.theme.ThemeViewModel
import com.islamicapp.siraathalmusthaqeem.ui.theme.SiraathalMusthaqeemTheme
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        preferencesManager = PreferencesManager(applicationContext)

        setContent {
            val themeViewModel: ThemeViewModel = koinViewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            // Apply saved dark/light theme dynamically
            SiraathalMusthaqeemTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation(preferencesManager)
                }
            }
        }
    }
}
