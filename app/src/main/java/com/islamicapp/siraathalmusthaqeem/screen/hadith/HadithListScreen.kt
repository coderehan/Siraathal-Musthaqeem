package com.islamicapp.siraathalmusthaqeem.screen.hadith

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.domain.hadith.model.Hadith
import com.islamicapp.siraathalmusthaqeem.screen.hadith.components.HadithListItem
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HadithListScreen(
    navController: NavController,
    collectionType: HadithCollectionType
) {
    val viewModel: HadithViewModel = koinViewModel(
        key = "HadithViewModel_${collectionType.name}"
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(collectionType) {
        viewModel.loadHadiths(collectionType)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(8.dp)
    ) {
        when (uiState) {
            is UIState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) { SpinKitLoader() }

            is UIState.Success -> {
                val hadithList = (uiState as UIState.Success<List<Hadith>>).data
                    .filter { it.sectionId.isNotBlank() }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(hadithList) { section ->
                        HadithListItem(
                            hadith = section,
                            navController = navController,
                            collectionType = collectionType
                        )
                    }
                }
            }

            is UIState.Empty -> Text(
                "No data available",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Gray
            )

            is UIState.Failure -> Text(
                (uiState as UIState.Failure).throwable.message ?: "Error occurred",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}