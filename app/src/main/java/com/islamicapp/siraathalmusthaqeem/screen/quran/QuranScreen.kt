package com.islamicapp.siraathalmusthaqeem.screen.quran

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.screen.quran.juz.JuzListScreen
import com.islamicapp.siraathalmusthaqeem.screen.quran.surah.SurahListScreen
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.CommonTabLayout

@Composable
fun QuranScreen(
    navController: NavController
) {
    val quranTabs = listOf("Surah", "Juz")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CommonActionBar(title = "Quran", navController = navController)

        CommonTabLayout(
            tabTitles = quranTabs,
            isScrollable = false    // use equal-width tabs for Quran
        ) { index, _ ->
            when (index) {
                0 -> SurahListScreen(navController)
                1 -> JuzListScreen(navController)
            }
        }
    }
}