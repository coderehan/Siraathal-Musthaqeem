package com.islamicapp.siraathalmusthaqeem.screen.prophetsstories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
        Prophets("Prophet Adam (AS)", R.drawable.prophet_stories_1, "prophets_stories_1_prophet_adam.json"),
        Prophets("Prophet Nuh (AS)", R.drawable.prophet_stories_2, "prophets_stories_2_prophet_nuh.json"),
        Prophets("Prophet Hud (AS)", R.drawable.ic_android, "prophets_stories_3_prophet_hud.json"),
        Prophets("Prophet Saleh (AS)", R.drawable.prophet_stories_4, "prophets_stories_4_prophet_saleh.json"),
        Prophets("Prophet Ibrahim (AS)", R.drawable.ic_android, "prophets_stories_5_prophet_ibrahim.json"),
        Prophets("Prophet Lut (AS)", R.drawable.ic_android, "prophets_stories_6_prophet_lut.json"),
        Prophets("Prophet Yousuf (AS)", R.drawable.ic_android, "prophets_stories_7_prophet_yousuf.json"),
        Prophets("Prophet Ayyub (AS)", R.drawable.ic_android, "prophets_stories_8_prophet_ayyub.json"),
        Prophets("Prophet Yunus (AS)", R.drawable.ic_android, "prophets_stories_9_prophet_yunus.json"),
        Prophets("Prophet Shuaib (AS)", R.drawable.ic_android, "prophets_stories_10_prophet_shuaib.json"),
        Prophets("Prophet Musa (AS)", R.drawable.ic_android, "prophets_stories_11_prophet_musa.json"),
        Prophets("Prophet Shamwil (AS)", R.drawable.ic_android, "prophets_stories_12_prophet_musa_shamwil_daud.json"),
        Prophets("Prophet Daud (AS)", R.drawable.ic_android, "prophets_stories_13_prophet_daud.json"),
        Prophets("Prophet Sulaiman (AS)", R.drawable.ic_android, "prophets_stories_14_prophet_sulaiman.json")
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
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
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

