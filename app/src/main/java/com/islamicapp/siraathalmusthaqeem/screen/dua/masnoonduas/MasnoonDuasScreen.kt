package com.islamicapp.siraathalmusthaqeem.screen.dua.masnoonduas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuas
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.dua.masnoonduas.components.MasnoonDuasItem
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MasnoonDuasScreen() {
    val viewModel: MasnoonDuasViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val expandedIndex by viewModel.expandedIndex.collectAsState()

    // Load Duas once
    LaunchedEffect(Unit) {
        viewModel.loadMasnoonDuas()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp) // Only horizontal padding here
        ) {
            when (uiState) {
                is UIState.Loading<*> -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        SpinKitLoader()
                    }
                }

                is UIState.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    val duas = (uiState as UIState.Success<List<MasnoonDuas>>).data

                    val groupedDuas = duas.groupBy { it.syllabusCategory }

                    LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
                        groupedDuas.forEach { (category, duasInGroup) ->

                            // 1. Display the Styled Category Header Card (e.g., GRADE 1)
                            item {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                    colors = CardDefaults.cardColors( colorResource(id = R.color.green)) // Blue background
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center // Center text inside Box
                                    ) {
                                        Text(
                                            text = category ?: "MISCELLANEOUS",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.White, // White text
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }

                            // 2. Display all Duas in this group
                            itemsIndexed(duasInGroup) { index, dua ->
                                // Calculate the global index of this dua within the entire list
                                val globalIndex = duas.indexOf(dua)

                                MasnoonDuasItem(
                                    dua = dua,
                                    isExpanded = globalIndex == expandedIndex,
                                    onClick = { viewModel.toggleExpansion(globalIndex) }
                                )
                            }

                            // Add a separator space between groups
                            item {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
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
                        Text("Error: $message", color = Color.Red)
                    }
                }
            }
        }
    }
}