package com.islamicapp.siraathalmusthaqeem.screen.quran.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.islamicapp.domain.quran.model.Surah
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun SurahDetailItem(
    surah: Surah
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = surah.surahNameEnglish,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Meaning: ${surah.englishNameTranslation}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF374151))
                    )

                    Text(
                        text = "Revealed: The ${surah.revelationType}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF374151))
                    )

                    Text(
                        text = "Total Ayahs: ${surah.numberOfAyahs}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF374151))
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                val imageRes = if (surah.revelationType.equals("Meccan", ignoreCase = true)) {
                    R.drawable.makkah
                } else {
                    R.drawable.madinah
                }

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(4.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

