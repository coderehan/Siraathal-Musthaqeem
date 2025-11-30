package com.islamicapp.siraathalmusthaqeem.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
            navController.navigate(NavigationItem.Onboarding.route) {
                popUpTo(NavigationItem.Splash.route) { inclusive = true }
            }
        } else if (!profileDone) {
            navController.navigate(NavigationItem.Profile.route) {
                popUpTo(NavigationItem.Splash.route) { inclusive = true }
            }
        } else {
            navController.navigate(NavigationItem.Home.route) {
                popUpTo(NavigationItem.Splash.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF072120)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen),
            contentDescription = "App Logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}