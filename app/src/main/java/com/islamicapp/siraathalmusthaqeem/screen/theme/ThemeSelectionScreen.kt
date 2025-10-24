package com.islamicapp.siraathalmusthaqeem.screen.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.ui.theme.SiraathalMusthaqeemTheme
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionScreen(
    navController: NavController,
    isDarkTheme: Boolean,
    onThemeSelected: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            CommonActionBar(title = "Choose Theme", navController = navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // --- Theme Preview Image ---
                Image(
                    painter = painterResource(id = R.drawable.ic_theme_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                )

                // --- Heading ---
                Text(
                    text = "Choose a theme",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    textAlign = TextAlign.Center
                )

                // --- Description ---
                Text(
                    text = "We highly recommend you to choose a dark theme. It is easier on the eyes and saves battery life. You can always change it later from the settings.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp
                    ),
                    textAlign = TextAlign.Center
                )

                // --- Theme Buttons ---
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onThemeSelected(true) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDarkTheme)
                                MaterialTheme.colorScheme.primary
                            else
                                Color(0xFF374151)
                        )
                    ) {
                        Text(
                            text = "Dark",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    OutlinedButton(
                        onClick = { onThemeSelected(false) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Light",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeSelectorWrapper(
    navController: NavController,
    viewModel: ThemeViewModel = koinViewModel()
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    SiraathalMusthaqeemTheme(darkTheme = isDarkTheme) {
        ThemeSelectionScreen(
            navController = navController,
            isDarkTheme = isDarkTheme,
            onThemeSelected = { selectedDark ->
                viewModel.setDarkTheme(selectedDark)
            }
        )
    }
}
