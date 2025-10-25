package com.islamicapp.siraathalmusthaqeem.screen.tasbeeh

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.tasbeeh.components.CustomTargetDialog
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasbeehScreen(
    navController: NavController,
    viewModel: TasbeehViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) { viewModel.init(context) }

    val state by viewModel.tasbeehState.collectAsState()
    var showCustomDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }

    val arabicFont = FontFamily(Font(R.font.indo_pak_quran))

    Scaffold(
        topBar = { CommonActionBar(title = "Tasbeeh", navController = navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Quran Verse (Arabic + Translation)
                Text(
                    text = "أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ",
                    style = TextStyle(
                        fontFamily = arabicFont,
                        fontSize = 28.sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E3A8A),
                        textAlign = TextAlign.Center
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "“Indeed, in the remembrance of Allah do hearts find peace.”",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E3A8A),
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "— Surah Ar-Ra’d (13:28)",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF6B7280),
                        fontWeight = FontWeight.Medium
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Target and Count
                Text(
                    text = "Target: ${state.target}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF6B7280)
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Count: ${state.count}",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937),
                        fontSize = 42.sp
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Tap Circle Animation
                var tapScale by remember { mutableStateOf(1f) }
                val animatedScale by animateFloatAsState(targetValue = tapScale)
                val borderColor by animateColorAsState(
                    targetValue = if (state.count % state.target == 0 && state.count != 0)
                        Color(0xFF10B981)
                    else Color(0xFF2563EB)
                )

                Box(
                    modifier = Modifier
                        .size(230.dp)
                        .scale(animatedScale)
                        .border(4.dp, borderColor, CircleShape)
                        .background(Color.White, CircleShape)
                        .clickable {
                            viewModel.incrementCount(context)
                            tapScale = 0.92f
                            coroutineScope.launch {
                                delay(100)
                                tapScale = 1f
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tap",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = borderColor
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { showCustomDialog = true }) {
                        Icon(
                            Icons.Default.Create,
                            contentDescription = "Custom Target",
                            tint = Color(0xFF2563EB)
                        )
                    }

                    IconButton(onClick = { showResetDialog = true }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Reset",
                            tint = Color(0xFFDC2626)
                        )
                    }

                    IconButton(onClick = { viewModel.toggleSound() }) {
                        Icon(
                            imageVector = if (state.soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                            contentDescription = "Sound",
                            tint = if (state.soundEnabled) Color(0xFF16A34A) else Color.Gray
                        )
                    }

                    IconButton(onClick = { viewModel.toggleVibration() }) {
                        Icon(
                            imageVector = Icons.Default.Vibration,
                            contentDescription = "Vibration",
                            tint = if (state.vibrationEnabled) Color(0xFF16A34A) else Color.Gray
                        )
                    }
                }
            }

            // Dialogs
            if (showCustomDialog) {
                CustomTargetDialog(
                    currentTarget = state.target,
                    onDismiss = { showCustomDialog = false },
                    onSave = {
                        viewModel.updateTarget(it)
                        showCustomDialog = false
                    }
                )
            }

            if (showResetDialog) {
                AlertDialog(
                    onDismissRequest = { showResetDialog = false },
                    title = { Text("Reset Tasbeeh?") },
                    text = { Text("Do you want to reset the Tasbeeh count to zero?") },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.resetTasbeeh()
                            showResetDialog = false
                        }) { Text("Yes") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showResetDialog = false }) { Text("No") }
                    }
                )
            }
        }
    }
}