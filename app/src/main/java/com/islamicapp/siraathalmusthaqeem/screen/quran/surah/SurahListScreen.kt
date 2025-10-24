package com.islamicapp.siraathalmusthaqeem.screen.quran.surah

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.islamicapp.domain.quran.model.Surah
import com.islamicapp.siraathalmusthaqeem.screen.quran.SurahViewModel
import com.islamicapp.siraathalmusthaqeem.screen.quran.components.SurahListItem
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SurahListScreen(
    navController: NavController
) {
    val viewModel: SurahViewModel = koinViewModel()

    val uiState by viewModel.uiState.collectAsState()

    // Load surah list once when screen launches
    LaunchedEffect(Unit) {
        viewModel.loadSurahList()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(8.dp)
    ) {
        when (uiState) {

            is UIState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                SpinKitLoader()
            }

            is UIState.Success -> {
                val surahList = (uiState as UIState.Success<List<Surah>>).data

                LazyColumn {
                    items(
                        items = surahList,
                        key = { it.surahNumber }
                    ) { surah ->
                        SurahListItem(
                            surah = surah,
                            navController = navController
                        )
                    }
                }

            }

            is UIState.Empty -> Text(
                "No surahs available",
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


