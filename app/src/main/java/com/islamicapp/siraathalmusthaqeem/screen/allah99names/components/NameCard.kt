package com.islamicapp.siraathalmusthaqeem.screen.allah99names.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A reusable Composable UI component that displays one of Allah's 99 names
 * inside a styled card.
 */

@Composable
fun NameCard(
    number: Int,
    arabic: String,
    transliteration: String,
    meaning: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = number.toString(), fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF6B7280))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = arabic, fontSize = 32.sp, fontWeight = FontWeight.Normal, color = Color(0xFF111827))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = transliteration, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color(0xFF374151))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = meaning, fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color(0xFF6B7280))
        }
    }
}
