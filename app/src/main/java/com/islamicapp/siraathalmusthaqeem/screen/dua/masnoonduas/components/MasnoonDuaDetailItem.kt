package com.islamicapp.siraathalmusthaqeem.screen.dua.masnoonduas.components

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
import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuas
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun MasnoonDuaDetailItem(dua: MasnoonDuas, index: Int) {
    val quranFont = FontFamily(Font(R.font.indo_pak_quran))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Dua number/title
            Text(
                text = "$index. ${dua.title}",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Arabic text (Right aligned)
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

            Spacer(modifier = Modifier.height(8.dp))

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
                    textAlign = TextAlign.Justify
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Translation
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Translation: ")
                    }
                    append(dua.translation)
                },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF374151),
                    textAlign = TextAlign.Justify
                )
            )
        }
    }
}