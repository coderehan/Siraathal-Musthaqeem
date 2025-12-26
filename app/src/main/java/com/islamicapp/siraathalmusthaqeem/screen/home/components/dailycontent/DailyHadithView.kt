package com.islamicapp.siraathalmusthaqeem.screen.home.components.dailycontent

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun DailyHadithView(
    viewModel: DailyContentViewModel,
    modifier: Modifier = Modifier
) {
    val hadith by viewModel.dailyHadith.collectAsState()

    hadith?.let { content ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hadith Text - Clean black text only
            Text(
                text = content.hadith_text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = Color.Black,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth()
            )

            // Decorative Divider
            HorizontalDivider(
                thickness = 1.dp,
                color = colorResource(id = R.color.green).copy(alpha = 0.3f)
            )

            // Reference with icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📚",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = content.reference,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    ),
                    color = colorResource(id = R.color.green),
                    textAlign = TextAlign.End
                )
            }
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
                    text = "📜",
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = "No hadith available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}