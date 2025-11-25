package com.islamicapp.siraathalmusthaqeem.screen.quran.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import com.islamicapp.domain.quran.model.Surah
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun SurahListItem(
    surah: Surah,
    navController: NavController
) {
    val quranFont = FontFamily(Font(R.font.indo_pak_quran))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("surah_detail/${surah.surahNumber}")
            }
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = colorResource(id = R.color.green),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = surah.surahNumber.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.white)
                )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = surah.surahNameEnglish,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.black)
                )
            )
            Text(
                text = "Verses: ${surah.numberOfAyahs} | ${surah.revelationType}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }

        Text(
            text = surah.surahNameArabic,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = quranFont,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                letterSpacing = 6.sp,
                color = colorResource(id = R.color.green)
            ),
            textAlign = TextAlign.End
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.LightGray.copy(alpha = 0.3f))
    )
}



