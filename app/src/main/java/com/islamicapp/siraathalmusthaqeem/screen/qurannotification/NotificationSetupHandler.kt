package com.islamicapp.siraathalmusthaqeem.screen.qurannotification

import androidx.compose.runtime.*

@Composable
fun NotificationSetupHandler(
    onComplete: () -> Unit = {}
) {
    var notificationPermissionGranted by remember { mutableStateOf(false) }
    var exactAlarmPermissionGranted by remember { mutableStateOf(false) }
    var setupComplete by remember { mutableStateOf(false) }

    // Step 1: Request notification permission
    if (!notificationPermissionGranted) {
        NotificationPermissionHandler(
            onPermissionGranted = {
                notificationPermissionGranted = true
            },
            onPermissionDenied = {
                // Continue anyway - user can enable later
                notificationPermissionGranted = true
            }
        )
    }

    // Step 2: Request exact alarm permission (after notification permission)
    if (notificationPermissionGranted && !exactAlarmPermissionGranted) {
        ExactAlarmPermissionHandler(
            onPermissionGranted = {
                exactAlarmPermissionGranted = true
            },
            onPermissionDenied = {
                // Continue anyway - will use inexact alarms
                exactAlarmPermissionGranted = true
            }
        )
    }

    // Step 3: Call onComplete when both are done
    LaunchedEffect(notificationPermissionGranted, exactAlarmPermissionGranted) {
        if (notificationPermissionGranted && exactAlarmPermissionGranted && !setupComplete) {
            setupComplete = true
            onComplete()
        }
    }
}