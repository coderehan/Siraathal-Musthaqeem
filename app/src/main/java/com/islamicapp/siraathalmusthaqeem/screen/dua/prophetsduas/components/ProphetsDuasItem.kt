package com.islamicapp.siraathalmusthaqeem.screen.dua.prophetsduas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islamicapp.domain.duas.prophetsduas.model.ProphetsDuas

@Composable
fun ProphetsDuasItem(
    prophet: ProphetsDuas,
    quranFont: FontFamily
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(vertical = 14.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = prophet.prophet_name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 18.sp
                ),
                textAlign = TextAlign.Center
            )
        }

        // Dua Cards for each Prophet
        prophet.duas.forEach { dua ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    // Title
                    Text(
                        text = dua.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF111827)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Arabic Dua
                    Text(
                        text = dua.arabic_dua,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = quranFont,
                            fontSize = 34.sp,
                            color = MaterialTheme.colorScheme.primary,
                            lineHeight = 42.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        textAlign = TextAlign.End
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Transliteration
                    Text(
                        text = dua.transliteration,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF374151)
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Translation
                    Text(
                        text = dua.translation,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF111827),
                            lineHeight = 20.sp
                        )
                    )
                }
            }
        }
    }
}