package com.islamicapp.siraathalmusthaqeem.screen.allah99names

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
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
import com.islamicapp.siraathalmusthaqeem.screen.allah99names.components.NameCard
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun Allah99NamesScreen(
    navController: NavController,
    viewModel: Allah99NamesViewModel = koinViewModel()
) {
    // Collect the UIState (Loading / Success / Empty / Failure) from viewmodel
    val uiState by viewModel.uiState.collectAsState()

    // Trigger data loading only once when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadAllah99Names()
    }

    Scaffold(
        topBar = {
            CommonActionBar(title = "Names of Allah", navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(paddingValues)
        ) {
            // Handle each UI state cleanly using a when() block
            when (uiState) {

                // Show a loading spinner while data is being fetched
                is UIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        SpinKitLoader()
                    }
                }

                // Show list when data is successfully loaded
                is UIState.Success -> {
                    val names = (uiState as UIState.Success).data
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(names) { name ->
                            NameCard(
                                number = name.number,
                                // Updated field names to match the new domain model
                                arabic = name.arabic,
                                transliteration = name.transliteration,
                                meaning = name.translation
                            )
                        }
                    }
                }

                // Show message when data is successfully fetched but empty
                is UIState.Empty -> {
                    Text(
                        text = "No data available",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray
                    )
                }

                // Show an error message when something goes wrong
                is UIState.Failure -> {
                    val errorMessage = (uiState as UIState.Failure).throwable.message
                    Text(
                        text = errorMessage ?: "Error occurred",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
