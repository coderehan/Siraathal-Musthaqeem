package com.islamicapp.siraathalmusthaqeem.screen.hadith

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.siraathalmusthaqeem.screen.hadith.components.HadithListScreen
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.CommonTabLayout
import org.koin.androidx.compose.koinViewModel

@Composable
fun HadithScreen(navController: NavController, viewModel: HadithViewModel = koinViewModel()) {
    val hadithTabs = listOf(
        "Sahih Al-Bukhari" to HadithCollectionType.BUKHARI,
        "Sahih Muslim" to HadithCollectionType.MUSLIM,
        "Sunan An-Nasai" to HadithCollectionType.NASAI,
        "Sunan Abi Dawud" to HadithCollectionType.ABU_DAWUD,
        "Jami'at Tirmidhi" to HadithCollectionType.TIRMIDHI
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CommonActionBar(title = "Hadith", navController = navController)

        CommonTabLayout(tabTitles = hadithTabs.map { it.first }) { index, _ ->
            val collectionType = hadithTabs[index].second
            HadithListScreen(
                collectionType = collectionType
            )
        }
    }
}
