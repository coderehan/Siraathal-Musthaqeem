package com.islamicapp.siraathalmusthaqeem.screen.quran.surah

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.quran.components.SurahDetailItem
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SurahDetailScreen(
    navController: NavController,
    surahNumber: Int,
    viewModel: SurahViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val quranFont = remember { FontFamily(Font(R.font.indo_pak_quran)) }

    LaunchedEffect(surahNumber) {
        viewModel.loadSurahDetail(surahNumber)
    }

    Scaffold(
        topBar = {
            val titleText = when (val state = uiState) {
                is UIState.Success -> state.data.firstOrNull()?.surahNameEnglish ?: "Surah Details"
                else -> "Surah Details"
            }
            CommonActionBar(title = titleText, navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is UIState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { SpinKitLoader() }

                is UIState.Success -> {
                    val surahDetailList = state.data

                    if (surahDetailList.isNotEmpty()) {
                        val surahHeader = surahDetailList.first()
                        val uniqueAyahs = surahDetailList
                            .distinctBy { it.numberInSurah }
                            .sortedBy { it.numberInSurah }

                        val bismillahText = "بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ"
                        var showBismillah = false
                        var remainingAyahs = uniqueAyahs

                        // Handle Bismillah display logic
                        if (surahHeader.surahNumber != 9) {
                            val firstAyah = uniqueAyahs.firstOrNull()
                            if (firstAyah != null && firstAyah.ayahText.contains(bismillahText)) {
                                showBismillah = true

                                // Remove Bismillah portion from first ayah (keep the remaining part)
                                val modifiedFirst = firstAyah.copy(
                                    ayahText = firstAyah.ayahText.replace(bismillahText, "").trim()
                                )
                                remainingAyahs = listOf(modifiedFirst) + uniqueAyahs.drop(1)
                            }
                        }

                        // Sajda marker inline map
                        val inlineContentMap = buildMap<String, InlineTextContent> {
                            remainingAyahs.forEach { ayah ->
                                if (ayah.hasSajda) {
                                    put(
                                        "sajdaMarker${ayah.numberInSurah}",
                                        InlineTextContent(
                                            Placeholder(
                                                width = 28.sp,
                                                height = 28.sp,
                                                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                                            )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(28.dp)
                                                    .background(
                                                        color = Color(0xFF10B981).copy(alpha = 0.15f),
                                                        shape = CircleShape
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "۩",
                                                    style = MaterialTheme.typography.bodyLarge.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(0xFF10B981)
                                                    )
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }

                        // Build annotated Quranic text
                        val fullText = buildAnnotatedString {
                            remainingAyahs.forEach { ayah ->
                                val cleanText = ayah.ayahText.trim().replace("۩", "").trim()
                                append(cleanText)
                                append(" • ")
                            }
                        }

                        // UI Layout
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item { SurahDetailItem(surah = surahHeader) }

                            // Centered Bismillah
                            if (showBismillah) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = bismillahText,
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontFamily = quranFont,
                                                color =  colorResource(id = R.color.green),
                                                fontSize = 38.sp
                                            ),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }

                            // Main Quran text
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .shadow(1.dp, RoundedCornerShape(12.dp))
                                        .background(Color.White, RoundedCornerShape(12.dp))
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        text = fullText,
                                        inlineContent = inlineContentMap,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontFamily = quranFont,
                                            color = Color(0xFF111827),
                                            lineHeight = 45.sp,
                                            fontSize = 40.sp
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        textAlign = TextAlign.Justify
                                    )
                                }
                            }
                        }
                    }
                }

                is UIState.Empty -> Text(
                    "No data available",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )

                is UIState.Failure -> Text(
                    state.throwable.message ?: "Error loading Surah",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            }
        }
    }
}
