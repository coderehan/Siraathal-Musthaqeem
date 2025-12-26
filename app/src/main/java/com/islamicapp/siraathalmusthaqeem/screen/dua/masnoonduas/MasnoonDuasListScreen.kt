package com.islamicapp.siraathalmusthaqeem.screen.dua.masnoonduas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuasTopic
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MasnoonDuasListScreen(
    navController: NavController,
    viewModel: MasnoonDuasViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load topics once
    LaunchedEffect(Unit) {
        viewModel.loadMasnoonDuasTopics()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            when (uiState) {
                is UIState.Loading<*> -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        SpinKitLoader()
                    }
                }

                is UIState.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    val topics = (uiState as UIState.Success<List<MasnoonDuasTopic>>).data

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFF9FAFB)),
                        contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            count = topics.size,
                            key = { index -> index }  // Use index to avoid duplicate key errors
                        ) { index ->
                            val topic = topics[index]
                            MasnoonTopicCard(
                                topic = topic,
                                onClick = {
                                    navController.navigate("masnoon_detail/${topic.topic}/$index")
                                }
                            )
                        }
                    }
                }

                is UIState.Empty<*> -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No topics found", color = Color.Gray)
                    }
                }

                is UIState.Failure<*> -> {
                    val message =
                        (uiState as UIState.Failure).throwable.message ?: "Error loading topics"
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: $message", color = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
fun MasnoonTopicCard(
    topic: MasnoonDuasTopic,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)  // Reduced height since no dua count
            .clickable { onClick() }
            .border(
                width = 1.5.dp,
                color = colorResource(id = R.color.green),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = topic.topic,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}