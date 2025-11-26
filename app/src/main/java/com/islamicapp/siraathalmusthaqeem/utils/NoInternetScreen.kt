package com.islamicapp.siraathalmusthaqeem.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun NoInternetScreen(
    modifier: Modifier = Modifier,
    onTryAgain: (() -> Unit)? = null
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.CloudOff,
            contentDescription = "No Internet Cloud Icon",
            modifier = Modifier.size(180.dp),
            tint = colorResource(id = R.color.green)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Ooops!",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Black,
                fontSize = 32.sp
            ),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "No Internet Connection found\nPlease check your network settings.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                // Open network settings
                openNetworkSettings(context)
                // Optionally call an external retry logic if provided
                onTryAgain?.invoke()
            },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green))
        ) {
            Text(text = "Try Again", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// Function to open network settings
private fun openNetworkSettings(context: Context) {
    // Attempt to open Wi-Fi settings directly
    var intent = Intent(Settings.ACTION_WIFI_SETTINGS)

    // Fallback to general settings if the specific action fails
    if (intent.resolveActivity(context.packageManager) == null) {
        intent = Intent(Settings.ACTION_SETTINGS)
    }

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Final, broad fallback just in case
        context.startActivity(Intent(Settings.ACTION_SETTINGS))
    }
}