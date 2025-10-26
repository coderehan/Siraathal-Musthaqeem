package com.islamicapp.siraathalmusthaqeem.screen.dua

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.islamicapp.siraathalmusthaqeem.screen.dua.emotionsduas.EmotionsDuasScreen
import com.islamicapp.siraathalmusthaqeem.screen.dua.masnoonduas.MasnoonDuasScreen
import com.islamicapp.siraathalmusthaqeem.screen.dua.prophetsduas.ProphetsDuasScreen
import com.islamicapp.siraathalmusthaqeem.screen.dua.quranicduas.QuranicDuasScreen
import com.islamicapp.siraathalmusthaqeem.screen.dua.rabbanaduas.RabbanaDuasScreen
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.CommonTabLayout

@Composable
fun DuaScreen(navController: NavHostController) {
    val duaTabs = listOf(
        "Masnoon Duas",
        "Emotions Duas",
        "40 Rabbana Duas",
        "Quranic Duas",
        "Prophets Duas"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CommonActionBar(title = "Duas", navController = navController)

        CommonTabLayout(tabTitles = duaTabs) { index, title ->
            when (index) {
                0 -> MasnoonDuasScreen(title = title)
                1 -> EmotionsDuasScreen(navController = navController)
                2 -> RabbanaDuasScreen()
                3 -> QuranicDuasScreen(title = title)
                4 -> ProphetsDuasScreen(title = title)
            }
        }
    }
}
