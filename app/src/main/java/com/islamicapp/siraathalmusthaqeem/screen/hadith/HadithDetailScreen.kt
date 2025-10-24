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

@Composable
fun HadithDetailScreen(
    navController: NavController,
    collectionType: HadithCollectionType,
    sectionId: String,
    viewModel: HadithViewModel = koinViewModel(
        key = "HadithDetail_${collectionType.name}"
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(collectionType) {
        viewModel.loadHadiths(collectionType)
    }

    Scaffold(
        topBar = {
            val titleText = when (val state = uiState) {
                is UIState.Success -> {
                    state.data.firstOrNull()?.sectionTitle ?: "Hadith Details"
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
            when (uiState) {
                is UIState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) { SpinKitLoader() }

                is UIState.Success -> {
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
                                referenceHadith = item.referenceHadith
                            )
                        }
                    }
                }

                is UIState.Empty -> Text("No hadiths found", Modifier.align(Alignment.Center))
                is UIState.Failure -> Text("Error loading hadiths", Modifier.align(Alignment.Center))
            }
        }
    }
}
