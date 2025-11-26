package com.islamicapp.siraathalmusthaqeem.screen.tasbeeh

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun TasbeehScreen(
    navController: NavController,
    viewModel: TasbeehViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Listen for toast events from the ViewModel
    LaunchedEffect(Unit) {
        viewModel.init(context)
        viewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

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
                    text = "أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ",
                    style = TextStyle(
                        fontFamily = arabicFont,
                        fontSize = 42.sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.green),
                        textAlign = TextAlign.Center,
                        letterSpacing = 4.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "“Indeed, in the remembrance of Allah do hearts find peace.”",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.black),
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
                    targetValue = if (state.count == state.target && state.count != 0)
                        colorResource(id = R.color.green)
                    else colorResource(id = R.color.green)
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
                            tint = colorResource(id = R.color.green)
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