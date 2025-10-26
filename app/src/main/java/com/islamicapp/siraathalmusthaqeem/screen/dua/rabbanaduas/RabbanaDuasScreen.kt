package com.islamicapp.siraathalmusthaqeem.screen.dua.rabbanaduas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.domain.duas.rabbanaduas.model.RabbanaDuas
import com.islamicapp.siraathalmusthaqeem.screen.dua.rabbanaduas.components.RabbanaDuasItem
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun RabbanaDuasScreen() {
    val viewModel: RabbanaDuasViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    // Load Duas once
    LaunchedEffect(Unit) {
        viewModel.loadRabbanaDuas()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            when (uiState) {
                is UIState.Loading<*> -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        SpinKitLoader()
                    }
                }

                is UIState.Success<*> -> {
                    val duas = (uiState as UIState.Success<List<RabbanaDuas>>).data
                    LazyColumn {
                        items(duas) { dua ->
                            RabbanaDuasItem(dua)
                        }
                    }

                }

                is UIState.Empty<*> -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No Duas found", color = Color.Gray)
                    }
                }

                is UIState.Failure<*> -> {
                    val message =
                        (uiState as UIState.Failure).throwable.message ?: "Error loading Duas"
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(message, color = Color.Red)
                    }
                }
            }
        }
    }
}