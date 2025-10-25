package com.islamicapp.siraathalmusthaqeem.screen.prophetsstories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.screen.prophetsstories.components.ProphetsStoriesDetailItem
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProphetsStoriesDetailScreen(
    navController: NavController,
    prophetName: String,
    fileName: String,
    viewModel: ProphetsStoriesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProphetStory(fileName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        CommonActionBar(title = prophetName, navController = navController)

        when (uiState) {
            is UIState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                SpinKitLoader()
            }

            is UIState.Success -> {
                val storyData = (uiState as UIState.Success).data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = storyData.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1F2937)
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    items(storyData.story.size) { index ->
                        val section = storyData.story[index]

                        // Case: introduction only (no points or verses)
                        if (section.introduction != null && section.points.isNullOrEmpty() && section.verses.isNullOrEmpty()) {
                            // Render only the blue header, not the white content card
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
                            ) {
                                Text(
                                    text = section.introduction!!,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 10.dp)
                                )
                            }
                        }

                        // Case: introduction + points
                        val pointsList = section.points
                        if (!pointsList.isNullOrEmpty()) {
                            val introTitle = section.introduction ?: "Section"
                            val contentBuilder = buildString {
                                pointsList.forEach { point ->
                                    if (point.text != null) {
                                        append("🌿 ${point.text}\n\n")
                                    }

                                    // Handle nested surahs under a point
                                    if (point.surah != null && !point.verses.isNullOrEmpty()) {
                                        append("📖 Surah: ${point.surah}\n")
                                        point.verses!!.forEach { verse ->
                                            append("   🕊 ${verse.text}\n")
                                        }
                                        append("\n")
                                    }
                                }
                            }

                            ProphetsStoriesDetailItem(title = introTitle, content = contentBuilder.trim())
                        }

                        // Case: direct surah + verses
                        val versesList = section.verses
                        if (section.surah != null && !versesList.isNullOrEmpty()) {
                            val surahTitle = "Surah: ${section.surah}"
                            val versesText = buildString {
                                versesList.forEach { verse ->
                                    append("🌿 ${verse.text}\n\n")
                                }
                            }

                            ProphetsStoriesDetailItem(title = surahTitle, content = versesText.trim())
                        }
                    }


                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            is UIState.Empty -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                Text("No story found", color = Color.Gray)
            }

            is UIState.Failure -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                val message = (uiState as UIState.Failure).throwable.message ?: "Error occurred"
                Text(message, color = Color.Red)
            }
        }
    }
}
