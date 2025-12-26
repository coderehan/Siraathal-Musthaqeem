package com.islamicapp.siraathalmusthaqeem.screen.home.components.dailycontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun DailyDuaView(
    viewModel: DailyContentViewModel,
    modifier: Modifier = Modifier
) {
    val dua by viewModel.dailyDua.collectAsState()
    val quranFont = FontFamily(Font(R.font.indo_pak_quran))

    dua?.let { content ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Arabic Text with Green Background - RTL Layout
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = content.arabic,
                        fontFamily = quranFont,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            lineHeight = 48.sp
                        ),
                        textAlign = TextAlign.Right,
                        color = colorResource(id = R.color.green),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Decorative Divider
            HorizontalDivider(
                thickness = 1.dp,
                color = colorResource(id = R.color.green).copy(alpha = 0.3f)
            )

            // Transliteration - LTR (Left to Right)
            Text(
                text = content.transliteration,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    lineHeight = 26.sp
                ),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth()
            )

            // Translation - LTR (Left to Right)
            Text(
                text = content.translation,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = Color.Black.copy(alpha = 0.87f),
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth()
            )
        }
    } ?: run {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "📖",
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = "No dua available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}