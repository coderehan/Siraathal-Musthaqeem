package com.islamicapp.siraathalmusthaqeem.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.navigation.NavigationItem
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, preferencesManager: PreferencesManager) {
    LaunchedEffect(Unit) {
        delay(3000) // simulate splash delay

        val onboardingDone = preferencesManager.isOnboardingCompleted()
        val profileDone = preferencesManager.isProfileCompleted()

        if (!onboardingDone) {
            // First time → go to onboarding
            navController.navigate(NavigationItem.Onboarding.route) {
                popUpTo(NavigationItem.Splash.route) { inclusive = true }
            }
        } else if (!profileDone) {
            // Onboarding done but no profile
            navController.navigate(NavigationItem.Profile.route) {
                popUpTo(NavigationItem.Splash.route) { inclusive = true }
            }
        } else {
            // Already installed + profile done
            navController.navigate(NavigationItem.Home.route) {
                popUpTo(NavigationItem.Splash.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.splash),
                contentDescription = "App Logo",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SIRAATHAL MUSTHAQEEM",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


