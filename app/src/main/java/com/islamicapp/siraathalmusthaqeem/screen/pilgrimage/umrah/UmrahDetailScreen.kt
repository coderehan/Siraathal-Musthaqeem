package com.islamicapp.siraathalmusthaqeem.screen.pilgrimage.umrah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.islamicapp.siraathalmusthaqeem.screen.pilgrimage.PilgrimageViewModel
import com.islamicapp.siraathalmusthaqeem.screen.pilgrimage.components.UmrahContent
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun UmrahDetailScreen(
    viewModel: PilgrimageViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPilgrimage("umrah.json")
    }

    when (uiState) {
        is UIState.Loading<*> -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            SpinKitLoader()
        }

        is UIState.Success<*> -> {
            val umrahData = (uiState as UIState.Success).data
            UmrahContent(umrahData)
        }

        is UIState.Empty<*> -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text("No data found for Umrah.", color = Color.Gray)
        }

        is UIState.Failure<*> -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            val message = (uiState as UIState.Failure).throwable.message ?: "Error occurred"
            Text(message, color = Color.Red)
        }
    }
}