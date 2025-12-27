package com.islamicapp.siraathalmusthaqeem.screen.qurannotification

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun ExactAlarmPermissionHandler(
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {}
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var hasChecked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasChecked) {
            hasChecked = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                if (!alarmManager.canScheduleExactAlarms()) {
                    showDialog = true
                } else {
                    onPermissionGranted()
                }
            } else {
                // Android 11 and below - no permission needed
                onPermissionGranted()
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                onPermissionDenied()
            },
            title = { Text("⏰ Exact Timing Permission") },
            text = {
                Text(
                    "For precise notification timing (exactly at 6 AM, 7 PM, etc.), " +
                            "we need permission to schedule exact alarms.\n\n" +
                            "This ensures you get reminders at the exact scheduled times."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                            context.startActivity(intent)
                        }
                        onPermissionGranted()
                    }
                ) {
                    Text("Grant Permission")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onPermissionDenied()
                    }
                ) {
                    Text("Later")
                }
            }
        )
    }
}