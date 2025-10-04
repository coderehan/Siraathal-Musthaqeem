package com.islamicapp.siraathalmusthaqeem.screen.hijricalendar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HijriCalendarScreen(
    viewModel: HijriCalendarViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Fetch data on first composition
    LaunchedEffect(Unit) {
        viewModel.getCurrentHijriCalendarDate()
    }

    when (val state = uiState) {
        is UIState.Loading -> {
            DateContentLoading()
        }
        is UIState.Success -> {
            DateContentSuccess(
                hijriDate = "${state.data.hijriDay} ${state.data.hijriMonth} ${state.data.hijriYear} AH",
                gregorianDate = "${state.data.englishDay} ${state.data.englishMonth} ${state.data.englishYear}, ${state.data.englishWeekday}"
            )
        }
        is UIState.Failure -> {
            DateContentError()
        }

        is UIState.Empty<*> -> TODO()
    }
}

@Composable
private fun DateContentSuccess(
    hijriDate: String,
    gregorianDate: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = hijriDate,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
        Text(
            text = gregorianDate,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DateContentLoading() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            strokeWidth = 2.dp
        )
    }
}

@Composable
private fun DateContentError() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Failed to load date",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.error
        )
    }
}