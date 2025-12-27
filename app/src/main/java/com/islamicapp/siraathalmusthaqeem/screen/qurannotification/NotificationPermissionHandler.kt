package com.islamicapp.siraathalmusthaqeem.screen.qurannotification

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager

@Composable
fun NotificationPermissionHandler(
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {}
) {
    val context = LocalContext.current
    var showRationale by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var permissionRequested by remember { mutableStateOf(false) }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            // Permission denied - show settings dialog
            showSettingsDialog = true
            onPermissionDenied()
        }
    }

    // Check and request permission on first composition
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission already granted
                    onPermissionGranted()
                }
                else -> {
                    // Request permission
                    if (!permissionRequested) {
                        permissionRequested = true
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }
        } else {
            // Android 12 and below - no permission needed
            onPermissionGranted()
        }
    }

    // Rationale dialog
    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            title = { Text("🔔 Enable Notifications") },
            text = {
                Text(
                    "We need notification permission to send you daily Quran reading reminders:\n\n" +
                            "• Surah Yaseen at 6:00 AM\n" +
                            "• Surah Waqiah at 7:00 PM\n" +
                            "• Surah Mulk at 9:00 PM\n" +
                            "• Surah Kahf at 12:00 PM (Fridays)\n\n" +
                            "These reminders will help you build a consistent Quran reading habit."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showRationale = false
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                ) {
                    Text("Allow")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showRationale = false
                        onPermissionDenied()
                    }
                ) {
                    Text("Not Now")
                }
            }
        )
    }

    // Settings dialog (when permission is permanently denied)
    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text("⚠️ Notification Permission Required") },
            text = {
                Text(
                    "Notification permission was denied. To receive Quran reading reminders, " +
                            "please enable notifications in app settings.\n\n" +
                            "Settings → Apps → Siraathal Musthaqeem → Notifications → Allow"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSettingsDialog = false
                        // Open app settings
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSettingsDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}