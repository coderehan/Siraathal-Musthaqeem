package com.islamicapp.siraathalmusthaqeem.screen.seerah

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import com.islamicapp.siraathalmusthaqeem.screen.seerah.components.SeerahDetailItem
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun SeerahDetailScreen(
    navController: NavController,
    chapterTitle: String,
    fileName: String,
    viewModel: SeerahViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Decode the chapter title so it displays correctly in the action bar
    val decodedTitle = remember(chapterTitle) {
        try {
            URLDecoder.decode(chapterTitle, StandardCharsets.UTF_8.toString())
        } catch (e: Exception) {
            chapterTitle // Fallback if decoding fails
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadSeerah(fileName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        // Use the decodedTitle here
        CommonActionBar(title = decodedTitle, navController = navController)

        when (uiState) {
            is UIState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                SpinKitLoader()
            }

            is UIState.Success -> {
                val seerahData = (uiState as UIState.Success).data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Use the indexed items function
                    items(seerahData.size) { index ->
                        val seerahItem = seerahData[index]

                        // The 'topic' is the title for the blue card
                        val title = seerahItem.topic ?: "Section"

                        // Build the content for the white card
                        val contentBuilder = buildString {
                            // Add the main content
                            if (!seerahItem.content.isNullOrBlank()) {
                                append(seerahItem.content!!.trim())
                            }

                            // Add the references, if they exist
                            val references = seerahItem.reference
                            if (!references.isNullOrEmpty()) {
                                // Add spacing if there was content before
                                if (this.isNotBlank()) {
                                    append("\n\n")
                                }
                                append("--- References ---\n")
                                references.forEach { (key, value) ->
                                    append("$key: $value\n")
                                }
                            }
                        }

                        // Pass the formatted title and content to the item
                        SeerahDetailItem(
                            title = title,
                            content = contentBuilder.trim()
                        )
                    }

                    // Add spacer at the end of the list
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            is UIState.Empty -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                Text("No data found for this chapter.", color = Color.Gray)
            }

            is UIState.Failure -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                val message = (uiState as UIState.Failure).throwable.message ?: "Error occurred"
                Text(message, color = Color.Red)
            }
        }
    }
}