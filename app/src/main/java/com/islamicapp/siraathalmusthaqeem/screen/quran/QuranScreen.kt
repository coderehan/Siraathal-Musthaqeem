package com.islamicapp.siraathalmusthaqeem.screen.quran

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.quran.components.QuickAccessSurahs
import com.islamicapp.siraathalmusthaqeem.screen.quran.juz.JuzListScreen
import com.islamicapp.siraathalmusthaqeem.screen.quran.surah.SurahListScreen
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.CommonTabLayout
import androidx.compose.material3.Scaffold
import androidx.navigation.NavHostController
import com.islamicapp.siraathalmusthaqeem.screen.home.components.HomeBottomBar

@Composable
fun QuranScreen(
    navController: NavController
) {
    val quranTabs = listOf("Surah", "Juz")

    Scaffold(
        topBar = {
            CommonActionBar(title = "Quran", navController = navController)
        },
        bottomBar = {
            HomeBottomBar(navController = navController as NavHostController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {
            // Quick Access section (ABOVE tab layout)
            Text(
                text = "Quick Access",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black),
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            QuickAccessSurahs(navController = navController)

            Spacer(modifier = Modifier.height(8.dp))

            // Tabs for Surah / Juz
            CommonTabLayout(
                tabTitles = quranTabs,
                isScrollable = false
            ) { index, _ ->
                when (index) {
                    0 -> SurahListScreen(navController)
                    1 -> JuzListScreen(navController)
                }
            }
        }
    }
}