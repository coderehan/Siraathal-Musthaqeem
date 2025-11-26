package com.islamicapp.siraathalmusthaqeem.screen.hadith.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun HadithDetailItem(
    hadithNumber: Double,
    hadithText: String,
    referenceBook: Int,
    referenceHadith: Int,
    collectionName: String
) {
    val context = LocalContext.current // Get context for sharing

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // --- Hadith Number and Share Button Row ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hadith No: ${hadithNumber.toInt()}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.green)
                )

                IconButton(
                    onClick = {
                        shareHadith(
                            context,
                            collectionName,
                            hadithNumber,
                            hadithText,
                            referenceBook,
                            referenceHadith
                        )
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Hadith",
                        tint = colorResource(id = R.color.green)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = hadithText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                lineHeight = 20.sp,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_book),
                    contentDescription = "Reference",
                    tint = colorResource(id = R.color.green),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    // Displaying the Hadith Collection Name/Book is recommended here too,
                    // but it's not available in this function yet.
                    text = "Reference: Book $referenceBook, Hadith $referenceHadith",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

// Helper function to handle sharing logic
private fun shareHadith(
    context: Context,
    collectionName: String,
    hadithNumber: Double,
    hadithText: String,
    referenceBook: Int,
    referenceHadith: Int
) {
    val appPackageName = context.packageName
    val playStoreLink = "https://play.google.com/store/apps/details?id=$appPackageName"

    // --- UPDATED SHARE TEXT to include Hadith Collection Name ---
    val shareText = "Hadith Collection: $collectionName\n" + // ADDED THIS LINE
            "Hadith No: ${hadithNumber.toInt()}\n" +
            "Reference: Book $referenceBook, Hadith $referenceHadith\n\n" +
            hadithText +
            "\n\n— Shared via Siraathal Musthaqeem App\n" +
            "Download Siraathal Musthaqeem App from Playstore:\n$playStoreLink"

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}