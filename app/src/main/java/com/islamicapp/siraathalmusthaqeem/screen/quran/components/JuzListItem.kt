package com.islamicapp.siraathalmusthaqeem.screen.quran.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun JuzListItem(
    juzNumber: Int,
    arabicName: String,
    englishName: String,
    navController: NavController,
    quranFont: FontFamily
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .clickable {
                // Navigate to PDF viewer with juz number
                navController.navigate("quran_pdf_reader?juzNumber=$juzNumber")
            }
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
                text = juzNumber.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.white)
                )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Juz-$juzNumber",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.black)
                )
            )
            Text(
                text = englishName,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }

        Text(
            text = arabicName,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = quranFont,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.green),
                fontSize = 40.sp,
                letterSpacing = 6.sp
            ),
            textAlign = TextAlign.End,
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.CenterVertically)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.LightGray.copy(alpha = 0.3f))
    )
}