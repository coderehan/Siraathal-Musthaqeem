package com.islamicapp.siraathalmusthaqeem.screen.dua.emotionsduas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.domain.duas.emotionsduas.model.EmotionsDuas
import com.islamicapp.siraathalmusthaqeem.screen.dua.emotionsduas.components.EmotionsDuasDetailItem
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmotionsDuasDetailScreen(
    navController: NavController,
    emotionName: String,
    fileName: String,
    viewModel: EmotionsDuasViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load dua JSON when screen opens
    LaunchedEffect(fileName) {
        viewModel.loadEmotionsDuas(fileName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CommonActionBar(
            title = emotionName.replaceFirstChar { it.uppercase() },
            navController = navController
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(12.dp)
        ) {
            when (uiState) {
                is UIState.Loading<*> -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }

                is UIState.Success<*> -> {
                    val duas = (uiState as UIState.Success<List<EmotionsDuas>>).data

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(duas) { index, dua ->
                            EmotionsDuasDetailItem(dua = dua, index = index + 1)
                        }
                    }
                }

                is UIState.Empty -> Text(
                    "No duas found for this emotion.",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )

                is UIState.Failure -> Text(
                    (uiState as UIState.Failure).throwable.message ?: "Error loading duas.",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}