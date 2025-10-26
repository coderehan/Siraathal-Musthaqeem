package com.islamicapp.siraathalmusthaqeem.screen.dua.quranicduas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.domain.duas.quranicduas.model.QuranicDuas
import com.islamicapp.siraathalmusthaqeem.screen.dua.quranicduas.components.QuranicDuasDetailItem
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuranicDuasDetailScreen(
    navController: NavController,
    categoryName: String,
    fileName: String,
    viewModel: QuranicDuasViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load the dua JSON file on screen launch
    LaunchedEffect(fileName) {
        viewModel.loadQuranicDuas(fileName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CommonActionBar(
            title = categoryName.replaceFirstChar { it.uppercase() },
            navController = navController
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(12.dp)
        ) {
            when (uiState) {
                is UIState.Loading<*> -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    SpinKitLoader()
                }

                is UIState.Success<*> -> {
                    val duas = (uiState as UIState.Success<List<QuranicDuas>>).data

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(duas) { index, dua ->
                            QuranicDuasDetailItem(
                                dua = dua,
                                index = index + 1
                            )
                        }
                    }
                }

                is UIState.Empty -> Text(
                    text = "No duas found for this category.",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )

                is UIState.Failure -> Text(
                    text = (uiState as UIState.Failure).throwable.message
                        ?: "Error loading Quranic duas.",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
