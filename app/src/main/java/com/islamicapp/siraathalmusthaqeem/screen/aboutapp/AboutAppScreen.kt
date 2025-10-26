package com.islamicapp.siraathalmusthaqeem.screen.aboutapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar

@Composable
fun AboutAppScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CommonActionBar(
                title = "About the App",
                navController = navController
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // App Logo
            Image(
                painter = painterResource(id = R.drawable.ic_android),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 12.dp)
            )

            Text(
                text = "Siraathal Musthaqeem",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Version 1.0",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // App Description
            Text(
                text = "Siraathal Musthaqeem is your all-in-one Islamic companion app, designed to guide you on the straight path every day. — it’s your digital companion in faith.\n\n" +
                        "Our mission is to help Muslims around the world stay spiritually connected through authentic Islamic knowledge and a seamless modern design.\n\n" +
                        "🌿 Features include:\n" +
                        "• Complete Qur’an with Indo-Pak script\n" +
                        "• Powerful Duas from the Qur’an, Sunnah, and Prophets\n" +
                        "• Salah timings and Qibla direction\n" +
                        "• 99 Names of Allah with meanings\n" +
                        "• Hadith and Seerah of the Prophet ﷺ\n" +
                        "• Islamic reminders and daily inspirations\n\n" +
                        "Whether you’re at home, work, or traveling, Siraathal Musthaqeem keeps the light of guidance close to your heart.\n\n",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF111827),
                    lineHeight = 22.sp
                ),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(16.dp))

            val quranFont = FontFamily(Font(R.font.indo_pak_quran))

            Text(
                text = "ٱهۡدِنَا ٱلصِّرَ ٰ⁠طَ ٱلۡمُسۡتَقِیمَ",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = quranFont,
                    fontSize = 34.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "“Guide us to the Straight Path” — Qur’an 1:6",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF111827),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Contact Section
            Text(
                text = "Have feedback or ideas?",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Reach out at:",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF374151))
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "mohammedrehanm1137@gmail.com",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Built with ❤️",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
