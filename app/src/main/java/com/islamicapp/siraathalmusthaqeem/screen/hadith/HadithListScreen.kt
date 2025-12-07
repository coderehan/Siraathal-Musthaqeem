package com.islamicapp.siraathalmusthaqeem.screen.hadith

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.domain.hadith.model.Hadith
import com.islamicapp.siraathalmusthaqeem.screen.hadith.components.HadithListItem
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel
import com.islamicapp.siraathalmusthaqeem.utils.NoInternetScreen
import com.islamicapp.core.utils.isNetworkAvailable

@Composable
fun HadithListScreen(
    navController: NavController,
    collectionType: HadithCollectionType
) {
    val viewModel: HadithViewModel = koinViewModel(
        key = "HadithViewModel_${collectionType.name}"
    )
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()
    // Initialize state using the core function
    var networkAvailable by remember { mutableStateOf(isNetworkAvailable(context)) }

    LaunchedEffect(collectionType, networkAvailable) {
        // Only load data if the network is available
        if (networkAvailable) {
            viewModel.loadHadiths(collectionType)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(8.dp)
    ) {
        when {
            // 1. NO INTERNET (Highest Priority for API-dependent feature)
            !networkAvailable -> {
                NoInternetScreen(
                    modifier = Modifier.align(Alignment.Center),
                    onTryAgain = {
                        // Re-check network status when the user clicks 'Try Again'
                        val isConnected = isNetworkAvailable(context)
                        if (isConnected) {
                            networkAvailable = true
                            // If network is back, the LaunchedEffect will trigger viewModel.loadHadiths()
                        }
                    }
                )
            }

            // 2. Loading State
            uiState is UIState.Loading -> Box(
                Modifier.fillMaxSize(),
                Alignment.Center
            ) { SpinKitLoader() }

            // 3. Success State
            uiState is UIState.Success -> {
                val hadithList = (uiState as UIState.Success<List<Hadith>>).data
                    .filter { it.sectionId.isNotBlank() }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        start = 0.dp,
                        end = 0.dp,
                        top = 10.dp,
                        bottom = 60.dp
                    ),
                )
                {
                    items(hadithList) { section ->
                        HadithListItem(
                            hadith = section,
                            navController = navController,
                            collectionType = collectionType
                        )
                    }
                }
            }

            // 4. Empty State
            uiState is UIState.Empty -> Text(
                "No data available",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Gray
            )

            // 5. Failure State
            uiState is UIState.Failure -> Text(
                (uiState as UIState.Failure).throwable.message ?: "Error occurred",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}