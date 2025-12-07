package com.islamicapp.siraathalmusthaqeem.screen.dua.prophetsduas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel
import com.islamicapp.siraathalmusthaqeem.screen.dua.prophetsduas.components.ProphetsDuasItem

@Composable
fun ProphetsDuasScreen(viewModel: ProphetsDuasViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val quranFont = remember { FontFamily(Font(R.font.indo_pak_quran)) }

    LaunchedEffect(Unit) {
        viewModel.loadProphetsDuas()
    }

    Scaffold(
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
        ) {
            when (val state = uiState) {
                is UIState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { SpinKitLoader() }

                is UIState.Success -> {
                    val prophets = state.data

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        items(prophets) { prophet ->
                            ProphetsDuasItem(
                                prophet = prophet,
                                quranFont = quranFont
                            )
                        }
                    }
                }

                is UIState.Empty -> Text(
                    text = "No duas found",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )

                is UIState.Failure -> Text(
                    text = state.throwable.message ?: "Error loading duas",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            }
        }
    }
}