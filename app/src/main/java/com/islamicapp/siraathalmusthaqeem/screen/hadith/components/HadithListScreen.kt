package com.islamicapp.siraathalmusthaqeem.screen.hadith.components

import androidx.compose.foundation.background
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
import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.domain.hadith.model.Hadith
import com.islamicapp.siraathalmusthaqeem.screen.hadith.HadithViewModel
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HadithListScreen(
    collectionType: HadithCollectionType,
) {
    // Unique ViewModel per tab (fixes recomposition cross-over)
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
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(hadithList) { section -> HadithSectionItem(section) }
                }
            }
            is UIState.Empty -> Text("No data available", Modifier.align(Alignment.Center), Color.Gray)
            is UIState.Failure -> Text(
                (uiState as UIState.Failure).throwable.message ?: "Error occurred",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun HadithSectionItem(hadith: Hadith) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circular section ID
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = hadith.sectionId,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Section details (title + hadith range)
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = hadith.sectionTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1F2937)
            )
            Text(
                text = "${hadith.hadithNumberFirst.toInt()} - ${hadith.hadithNumberLast.toInt()}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}
