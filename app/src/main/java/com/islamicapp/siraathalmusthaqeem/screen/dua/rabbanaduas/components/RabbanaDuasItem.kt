package com.islamicapp.siraathalmusthaqeem.screen.dua.rabbanaduas.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islamicapp.domain.duas.rabbanaduas.model.RabbanaDuas
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun RabbanaDuasItem(dua: RabbanaDuas) {
    val quranFont = FontFamily(Font(R.font.indo_pak_quran))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header
            if (!dua.header.isNullOrBlank()) {
                Text(
                    text = dua.header!!,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Arabic Text (Qur’anic Font)
            Text(
                text = dua.arabicText,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = quranFont,
                    fontSize = 42.sp,
                    color = Color.White,
                    lineHeight = 60.sp
                ),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.green), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Transliteration
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))) {
                        append("Transliteration: ")
                    }
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF374151)
                        )
                    ) {
                        append(dua.transliteration)
                    }
                },
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Translation
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))) {
                        append("Translation: ")
                    }
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF374151)
                        )
                    ) {
                        append(dua.translation)
                    }
                },
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Recommended Use
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))) {
                        append("Recommended Use: ")
                    }
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF6B7280)
                        )
                    ) {
                        append(dua.recommendedUse)
                    }
                },
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
