package com.islamicapp.siraathalmusthaqeem

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.islamicapp.siraathalmusthaqeem.screen.navigation.Navigation
import com.islamicapp.siraathalmusthaqeem.screen.qurannotification.NotificationSetupHandler
import com.islamicapp.siraathalmusthaqeem.screen.theme.ThemeViewModel
import com.islamicapp.siraathalmusthaqeem.ui.theme.SiraathalMusthaqeemTheme
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private lateinit var preferencesManager: PreferencesManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Set system navigation bar to black (home button, back button, recent button)
        window.navigationBarColor = android.graphics.Color.BLACK
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightNavigationBars = false // false = white icons for system nav bar
        }

        preferencesManager = PreferencesManager(applicationContext)

        setContent {
            val themeViewModel: ThemeViewModel = koinViewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            // Track if permissions are being handled
            var permissionsHandled by remember { mutableStateOf(false) }

            // Apply saved dark/light theme dynamically
            SiraathalMusthaqeemTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Request notification permissions first (only shows on first launch)
                    if (!permissionsHandled) {
                        NotificationSetupHandler(
                            onComplete = {
                                permissionsHandled = true
                            }
                        )
                    }

                    // Your existing navigation (always rendered)
                    Navigation(preferencesManager)
                }
            }
        }
    }
}