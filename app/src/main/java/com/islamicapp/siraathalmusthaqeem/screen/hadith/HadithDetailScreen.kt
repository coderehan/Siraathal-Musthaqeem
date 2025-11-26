package com.islamicapp.siraathalmusthaqeem.screen.hadithdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.domain.hadith.model.Hadith
import com.islamicapp.siraathalmusthaqeem.screen.hadith.components.HadithDetailItem
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import com.islamicapp.siraathalmusthaqeem.screen.hadith.HadithViewModel
import org.koin.androidx.compose.koinViewModel
import com.islamicapp.siraathalmusthaqeem.utils.NoInternetScreen
import com.islamicapp.core.utils.isNetworkAvailable

// Helper extension function to get the display name of the Hadith book
fun HadithCollectionType.toDisplayName(): String {
    return when (this) {
        HadithCollectionType.BUKHARI -> "Sahih Al-Bukhari"
        HadithCollectionType.MUSLIM -> "Sahih Muslim"
        HadithCollectionType.NASAI -> "Sunan An-Nasai"
        HadithCollectionType.ABU_DAWUD -> "Sunan Abi Dawud"
        HadithCollectionType.TIRMIDHI -> "Jami'at Tirmidhi"
    }
}

@Composable
fun HadithDetailScreen(
    navController: NavController,
    collectionType: HadithCollectionType,
    sectionId: String,
    viewModel: HadithViewModel = koinViewModel(
        key = "HadithDetail_${collectionType.name}"
    )
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // State to track network availability
    var networkAvailable by remember { mutableStateOf(isNetworkAvailable(context)) }

    // Calculate the user-friendly book name once
    val collectionName = remember(collectionType) {
        collectionType.toDisplayName()
    }

    LaunchedEffect(collectionType, networkAvailable) {
        // Only load data if the network is available
        if (networkAvailable) {
            viewModel.loadHadiths(collectionType)
        }
    }

    Scaffold(
        topBar = {
            val titleText = when (val state = uiState) {
                is UIState.Success -> {
                    // Use the Hadith section title if available, otherwise fallback to the book name
                    state.data.firstOrNull()?.sectionTitle ?: collectionName
                }

                else -> "Hadith Details"
            }
            CommonActionBar(title = titleText, navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(paddingValues)
        ) {
            when {
                // 1. NO INTERNET (Highest Priority)
                !networkAvailable -> {
                    NoInternetScreen(
                        modifier = Modifier.align(Alignment.Center),
                        onTryAgain = {
                            val isConnected = isNetworkAvailable(context)
                            if (isConnected) {
                                networkAvailable = true
                            }
                        }
                    )
                }

                // 2. Loading
                uiState is UIState.Loading -> Box(
                    Modifier.fillMaxSize(),
                    Alignment.Center
                ) { SpinKitLoader() }

                // 3. Success
                uiState is UIState.Success -> {
                    val section = (uiState as UIState.Success<List<Hadith>>)
                        .data.firstOrNull { it.sectionId == sectionId }
                    val hadiths = section?.hadiths ?: emptyList()

                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(hadiths) { item ->
                            HadithDetailItem(
                                hadithNumber = item.hadithNumber,
                                hadithText = item.hadithText,
                                referenceBook = item.referenceBook,
                                referenceHadith = item.referenceHadith,
                                collectionName = collectionName
                            )
                        }
                    }
                }

                // 4. Empty/Failure States
                uiState is UIState.Empty -> Text(
                    "No hadiths found",
                    Modifier.align(Alignment.Center)
                )

                uiState is UIState.Failure -> Text(
                    "Error loading hadiths",
                    Modifier.align(Alignment.Center)
                )
            }
        }
    }
}