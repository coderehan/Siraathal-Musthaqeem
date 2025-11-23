package com.islamicapp.siraathalmusthaqeem.screen.dua.quranicduas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun QuranicDuasListScreen(navController: NavController) {

    val quranicDuaCategories = listOf(
        "Children",
        "Devotion",
        "Guidance",
        "Healing",
        "Hereafter",
        "Marriage",
        "Mercy",
        "Parents",
        "Patience",
        "Protection",
        "Repentance",
        "Righteous Company",
        "Sleep",
        "Strength"
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
            items(quranicDuaCategories) { category ->
                QuranicDuaCard(
                    category = category,
                    onClick = {
                        navController.navigate(
                            "quranic_dua_detail/${category}/${category.lowercase().replace(" ", "_")}.json"
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun QuranicDuaCard(
    category: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() }
            .border(
                width = 1.5.dp,
                color = colorResource(id = R.color.green),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
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
                text = category,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF111827),
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}
