package com.islamicapp.siraathalmusthaqeem.screen.seerah

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.screen.seerah.components.SeerahListItem
import com.islamicapp.siraathalmusthaqeem.screen.seerah.model.Seerah
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SeerahListScreen(
    navController: NavController
) {
    // The list of chapters as you requested
    val seerahChapters = listOf(
        Seerah("Chapter 1: A Brief Biography Of Prophet Muhammad SAW", "chapter_1.json"),
        Seerah("Chapter 2: Sublime Qualities Of The Prophet Muhammad SAW", "chapter_2.json"),
        Seerah("Chapter 3: Policies Of The Prophet At Times Of Conflict", "chapter_3.json"),
        Seerah("Chapter 4: Social Policies Of Prophet Muhammad SAW", "chapter_4.json"),
        Seerah("Chapter 5: Selected Sayings Of The Prophet Muhammad SAW", "chapter_5.json"),
        Seerah("Chapter 6: Prophet Muhammad SAW As Seen By Western Thinkers", "chapter_6.json")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CommonActionBar(title = "Seerath-un-Nabi", navController = navController)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(seerahChapters) { chapter ->
                SeerahListItem(
                    chapter = chapter,
                    onClick = {
                        // URL-encode the title to safely pass it as a nav argument
                        val encodedTitle =
                            URLEncoder.encode(chapter.title, StandardCharsets.UTF_8.toString())

                        // Navigate to the detail screen, passing title and filename
                        navController.navigate("seerah_detail/${encodedTitle}/${chapter.fileName}")
                    }
                )
            }
        }
    }
}