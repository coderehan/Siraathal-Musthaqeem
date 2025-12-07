package com.islamicapp.siraathalmusthaqeem.screen.pilgrimage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.islamicapp.siraathalmusthaqeem.screen.pilgrimage.hajj.HajjDetailScreen
import com.islamicapp.siraathalmusthaqeem.screen.pilgrimage.umrah.UmrahDetailScreen
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.CommonTabLayout

@Composable
fun PilgrimageScreen(
    navController: NavController
) {
    val pilgrimageTabs = listOf("Umrah", "Hajj")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 16.dp)
    ) {
        // Common Action Bar
        CommonActionBar(title = "Pilgrimage", navController = navController)

        // Tabs for Umrah / Hajj
        CommonTabLayout(
            tabTitles = pilgrimageTabs,
            isScrollable = false
        ) { index, _ ->
            when (index) {
                0 -> UmrahDetailScreen()
                1 -> HajjDetailScreen()
            }
        }
    }
}