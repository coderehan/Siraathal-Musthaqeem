package com.islamicapp.siraathalmusthaqeem.screen.dua.quranicduas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islamicapp.domain.duas.quranicduas.model.QuranicDuas
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun QuranicDuasDetailItem(
    dua: QuranicDuas,
    index: Int
) {
    val quranFont = FontFamily(Font(R.font.indo_pak_quran))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Dua number
            Text(
                text = "Dua #$index",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Arabic text (inside a highlighted box)
            Text(
                text = dua.arabic,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = quranFont,
                    fontSize = 42.sp,
                    color = Color.White,
                    lineHeight = 60.sp,
                    textAlign = TextAlign.End
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.green), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Transliteration
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Transliteration: ")
                    }
                    append(dua.transliteration)
                },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF374151),
                    lineHeight = 22.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Translation
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Translation: ")
                    }
                    append(dua.translation)
                },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF111827),
                    lineHeight = 22.sp
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Surah reference
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Surah: ")
                    }
                    append(dua.reference)
                },
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6B7280),
                    lineHeight = 18.sp
                )
            )
        }
    }
}
