package com.islamicapp.siraathalmusthaqeem.screen.quran.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.islamicapp.siraathalmusthaqeem.R
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun QuickAccessSurahs(navController: NavController) {
    val quickAccess = listOf(
        Triple("Surah Yaseen", "Every Morning", 36),
        Triple("Surah Waqiah", "Every Night", 56),
        Triple("Surah Mulk", "Every Night", 67),
        Triple("Surah Kahaf", "Every Friday", 18),
        Triple("Surah Rahman", "Everyday", 55)
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = quickAccess) { (name, note, number) ->
            QuickAccessCard(
                title = name,
                subtitle = note,
                onClick = {
                    // Navigate to PDF viewer with surah number
                    navController.navigate("quran_pdf_reader?surahNumber=$number")
                },
                iconRes = R.drawable.quran
            )
        }
    }
}

@Composable
fun QuickAccessCard(
    title: String,
    subtitle: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(80.dp)
            .clickable { onClick() }
            .border(
                width = 1.5.dp,
                color = colorResource(id = R.color.green),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.green))
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF111827)
                    ),
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
            }
        }
    }
}


