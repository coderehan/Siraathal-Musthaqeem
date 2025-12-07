package com.islamicapp.siraathalmusthaqeem.screen.prophetsstories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.prophetsstories.components.ProphetsStoriesListItem
import com.islamicapp.siraathalmusthaqeem.screen.prophetsstories.model.Prophets
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar

@Composable
fun ProphetsStoriesListScreen(
    navController: NavController
) {
    val prophetsList = listOf(
        Prophets("Prophet Adam\n(AS)", R.drawable.prophet_stories_1, "prophets_stories_1_prophet_adam.json"),
        Prophets("Prophet Nuh\n(AS)", R.drawable.prophet_stories_2, "prophets_stories_2_prophet_nuh.json"),
        Prophets("Prophet Hud\n(AS)", R.drawable.prophet_stories_3, "prophets_stories_3_prophet_hud.json"),
        Prophets("Prophet Saleh\n(AS)", R.drawable.prophet_stories_4, "prophets_stories_4_prophet_saleh.json"),
        Prophets("Prophet Ibrahim\n(AS)", R.drawable.prophet_stories_5, "prophets_stories_5_prophet_ibrahim.json"),
        Prophets("Prophet Lut\n(AS)", R.drawable.prophet_stories_6, "prophets_stories_6_prophet_lut.json"),
        Prophets("Prophet Yousuf\n(AS)", R.drawable.prophet_stories_7, "prophets_stories_7_prophet_yousuf.json"),
        Prophets("Prophet Ayyub\n(AS)", R.drawable.prophet_stories_8, "prophets_stories_8_prophet_ayyub.json"),
        Prophets("Prophet Yunus\n(AS)", R.drawable.prophet_stories_9, "prophets_stories_9_prophet_yunus.json"),
        Prophets("Prophet Shuaib\n(AS)", R.drawable.prophet_stories_10, "prophets_stories_10_prophet_shuaib.json"),
        Prophets("Prophet Musa\n(AS)", R.drawable.prophet_stories_11, "prophets_stories_11_prophet_musa.json"),
        Prophets("Prophet Shamwil\n(AS)", R.drawable.prophet_stories_12, "prophets_stories_12_prophet_musa_shamwil_daud.json"),
        Prophets("Prophet Daud\n(AS)", R.drawable.prophet_stories_13, "prophets_stories_13_prophet_daud.json"),
        Prophets("Prophet Sulaiman\n(AS)", R.drawable.prophet_stories_14, "prophets_stories_14_prophet_sulaiman.json")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        CommonActionBar(title = "Prophets Stories Mentioned In Quran", navController = navController)

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                top = 8.dp,
                bottom = 80.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(prophetsList) { prophet ->
                ProphetsStoriesListItem(
                    prophet = prophet,
                    onClick = {
                        navController.navigate("prophet_detail/${prophet.name}/${prophet.fileName}")
                    }
                )
            }
        }
    }
}

