package com.islamicapp.siraathalmusthaqeem.screen.dua.emotionsduas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EmotionsDuasListScreen(navController: NavController) {

    val emotionsList = listOf(
        "Anticipation", "Anxious", "Aroused", "Bored", "Confident",
        "Confused", "Content", "Curious", "Defeated", "Depressed",
        "Desire", "Desperate", "Determined", "Disbelief", "Doubtful",
        "Envious", "Grateful", "Greedy", "Guilty", "Happy",
        "Hatred", "Humiliated", "Hurt", "Hypocritical", "Impatient",
        "Indecisive", "Insecure", "Irritated", "Jealous", "Lazy",
        "Lonely", "Lost", "Love", "Nervous", "Nostalgic",
        "Offended", "Overwhelmed", "Peaceful", "Rage", "Regret",
        "Sad", "Satisfied", "Scared", "Suicidal", "Tired",
        "Uncertain", "Uneasy", "Unloved", "Weak"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(horizontal = 8.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(horizontal = 8.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(emotionsList) { emotion ->
                EmotionCard(
                    emotion = emotion,
                    onClick = {
                        // Navigate exactly like Prophet detail navigation
                        navController.navigate("emotion_detail/${emotion}/${emotion.lowercase()}.json")
                    }
                )
            }
        }
    }
}

@Composable
fun EmotionCard(
    emotion: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emotion,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF111827),
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

