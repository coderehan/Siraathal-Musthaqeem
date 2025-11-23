package com.islamicapp.siraathalmusthaqeem.screen.allah99names.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islamicapp.siraathalmusthaqeem.R

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
    val quranFont = FontFamily(Font(R.font.indo_pak_quran))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, colorResource(id = R.color.green))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Number inside a circle
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = colorResource(id = R.color.green),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.white)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Arabic text with Indo-Pak Quranic font
            Text(
                text = arabic,
                fontSize = 46.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF111827),
                fontFamily = quranFont,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Transliteration
            Text(
                text = transliteration,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF374151),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Meaning
            Text(
                text = meaning,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center
            )
        }
    }
}

