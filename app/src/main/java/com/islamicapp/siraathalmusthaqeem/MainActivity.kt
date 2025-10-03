package com.islamicapp.siraathalmusthaqeem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.islamicapp.siraathalmusthaqeem.screen.navigation.Navigation
import com.islamicapp.siraathalmusthaqeem.ui.theme.SiraathalMusthaqeemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SiraathalMusthaqeemTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation()
                }
            }
        }
    }
}
