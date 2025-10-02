package com.islamicapp.siraathalmusthaqeem.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.islamicapp.siraathalmusthaqeem.screen.allah99names.Allah99NamesScreen
import com.islamicapp.siraathalmusthaqeem.screen.dua.DuaScreen
import com.islamicapp.siraathalmusthaqeem.screen.hadith.HadithScreen
import com.islamicapp.siraathalmusthaqeem.screen.home.HomeScreen
import com.islamicapp.siraathalmusthaqeem.screen.masjid.MasjidScreen
import com.islamicapp.siraathalmusthaqeem.screen.splash.SplashScreen
import com.islamicapp.siraathalmusthaqeem.screen.onboarding.OnboardingScreen
import com.islamicapp.siraathalmusthaqeem.screen.pilgrimage.PilgrimageScreen
import com.islamicapp.siraathalmusthaqeem.screen.prayertimes.PrayerTimesScreen
import com.islamicapp.siraathalmusthaqeem.screen.profile.ProfileScreen
import com.islamicapp.siraathalmusthaqeem.screen.prophets.ProphetsScreen
import com.islamicapp.siraathalmusthaqeem.screen.qibla.QiblaScreen
import com.islamicapp.siraathalmusthaqeem.screen.quran.QuranScreen
import com.islamicapp.siraathalmusthaqeem.screen.seerah.SeerahScreen
import com.islamicapp.siraathalmusthaqeem.screen.tasbeeh.TasbeehScreen
import com.islamicapp.siraathalmusthaqeem.screen.zakath.ZakathScreen

@Composable
fun Navigation() {
    val navController: NavHostController = rememberNavController()

    // Navigation host where we define all routes/screens
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Splash.route // First screen is Splash
    ) {

        // Splash Screen
        composable(NavigationItem.Splash.route) {
            SplashScreen(navController = navController)
        }

        // Onboarding Screens
        composable(NavigationItem.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }

        // Profile Screen
        composable(NavigationItem.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // Home Screen
        composable(NavigationItem.Home.route) {
            HomeScreen(navController = navController)
        }

        // Prayer Times
        composable(NavigationItem.PrayerTimes.route) {
            PrayerTimesScreen(navController = navController)
        }

        // Qibla
        composable(NavigationItem.Qibla.route) {
            QiblaScreen(navController = navController)
        }

        // Masjid
        composable(NavigationItem.Masjid.route) {
            MasjidScreen(navController = navController)
        }

        // Allah 99 Names
        composable(NavigationItem.Allah99Names.route) {
            Allah99NamesScreen(navController = navController)
        }

        // Zakath
        composable(NavigationItem.Zakath.route) {
            ZakathScreen(navController = navController)
        }

        // Pilgrimage
        composable(NavigationItem.Pilgrimage.route) {
            PilgrimageScreen(navController = navController)
        }

        // Hadith
        composable(NavigationItem.Hadith.route) {
            HadithScreen(navController = navController)
        }

        // Seerah
        composable(NavigationItem.Seerah.route) {
            SeerahScreen(navController = navController)
        }

        // Prophets
        composable(NavigationItem.Prophets.route) {
            ProphetsScreen(navController = navController)
        }

        // Quran
        composable(NavigationItem.Quran.route) {
            QuranScreen(navController = navController)
        }

        // Dua
        composable(NavigationItem.Dua.route) {
            DuaScreen(navController = navController)
        }

        // Tasbeeh
        composable(NavigationItem.Tasbeeh.route) {
            TasbeehScreen(navController = navController)
        }

    }
}