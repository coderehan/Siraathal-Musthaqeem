package com.islamicapp.siraathalmusthaqeem.screen.dua.masnoonduas

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
import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuasTopic
import com.islamicapp.siraathalmusthaqeem.screen.dua.masnoonduas.components.MasnoonDuaDetailItem
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MasnoonDuasDetailScreen(
    navController: NavController,
    topicName: String,
    topicIndex: Int,
    viewModel: MasnoonDuasViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load topics if not already loaded
    LaunchedEffect(Unit) {
        if (uiState is UIState.Loading<*>) {
            viewModel.loadMasnoonDuasTopics()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CommonActionBar(
            title = topicName,
            navController = navController
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(horizontal = 12.dp)
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

                    // Get the selected topic by index
                    val selectedTopic = topics.getOrNull(topicIndex)

                    if (selectedTopic != null) {
                        val duas = selectedTopic.duas

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(top = 12.dp, bottom = 56.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            itemsIndexed(duas) { index, dua ->
                                MasnoonDuaDetailItem(
                                    dua = dua,
                                    index = index + 1
                                )
                            }
                        }
                    } else {
                        Text(
                            "Topic not found",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Gray
                        )
                    }
                }

                is UIState.Empty<*> -> {
                    Text(
                        "No duas found for this topic.",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray
                    )
                }

                is UIState.Failure<*> -> {
                    val message = (uiState as UIState.Failure).throwable.message ?: "Error loading duas"
                    Text(
                        "Error: $message",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}