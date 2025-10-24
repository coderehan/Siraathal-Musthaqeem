package com.islamicapp.siraathalmusthaqeem.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.siraathalmusthaqeem.screen.allah99names.Allah99NamesScreen
import com.islamicapp.siraathalmusthaqeem.screen.dua.DuaScreen
import com.islamicapp.siraathalmusthaqeem.screen.hadith.HadithScreen
import com.islamicapp.siraathalmusthaqeem.screen.hadithdetail.HadithDetailScreen
import com.islamicapp.siraathalmusthaqeem.screen.home.HomeScreen
import com.islamicapp.siraathalmusthaqeem.screen.masjid.MasjidScreen
import com.islamicapp.siraathalmusthaqeem.screen.splash.SplashScreen
import com.islamicapp.siraathalmusthaqeem.screen.onboarding.OnboardingScreen
import com.islamicapp.siraathalmusthaqeem.screen.pilgrimage.PilgrimageScreen
import com.islamicapp.siraathalmusthaqeem.screen.prayertimes.PrayerTimesScreen
import com.islamicapp.siraathalmusthaqeem.screen.profile.ProfileScreen
import com.islamicapp.siraathalmusthaqeem.screen.prophets.ProphetsScreen
import com.islamicapp.siraathalmusthaqeem.screen.quran.QuranScreen
import com.islamicapp.siraathalmusthaqeem.screen.seerah.SeerahScreen
import com.islamicapp.siraathalmusthaqeem.screen.tasbeeh.TasbeehScreen
import com.islamicapp.siraathalmusthaqeem.screen.zakath.ZakathScreen
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import com.islamicapp.siraathalmusthaqeem.screen.qibla.QiblaScreen
import com.islamicapp.siraathalmusthaqeem.screen.quran.juz.JuzDetailScreen
import com.islamicapp.siraathalmusthaqeem.screen.quran.surah.SurahDetailScreen
import com.islamicapp.siraathalmusthaqeem.screen.theme.ThemeSelectorWrapper

@Composable
fun Navigation(preferencesManager: PreferencesManager) {
    // NavController manages navigation between different screens in the app
    val navController: NavHostController = rememberNavController()

    // NavHost holds the navigation graph
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Splash.route
    ) {
        // Splash
        composable(NavigationItem.Splash.route) {
            SplashScreen(navController = navController, preferencesManager = preferencesManager)
        }

        // Onboarding
        composable(NavigationItem.Onboarding.route) {
            OnboardingScreen(navController = navController, preferencesManager = preferencesManager)
        }

        // Profile
        composable(NavigationItem.Profile.route) {
            ProfileScreen(navController = navController, preferencesManager = preferencesManager)
        }

        // Theme Selection
        composable("theme") {
            ThemeSelectorWrapper(navController = navController)
        }

        // Home
        composable(NavigationItem.Home.route) {
            HomeScreen(navController = navController, preferencesManager = preferencesManager)
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

        // Hadith Detail Screen
        composable(
            route = "hadith_detail/{collectionType}/{sectionId}",
            arguments = listOf(
                navArgument("collectionType") { type = NavType.StringType },
                navArgument("sectionId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val collectionType = backStackEntry.arguments?.getString("collectionType")!!
            val sectionId = backStackEntry.arguments?.getString("sectionId")!!

            HadithDetailScreen(
                navController = navController,
                collectionType = HadithCollectionType.valueOf(collectionType),
                sectionId = sectionId
            )
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

        // Surah Detail Screen
        composable(
            route = "surah_detail/{surahNumber}",
            arguments = listOf(
                navArgument("surahNumber") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val surahNumber = backStackEntry.arguments?.getInt("surahNumber") ?: 1

            SurahDetailScreen(
                navController = navController,
                surahNumber = surahNumber
            )
        }

        // Juz Detail Screen
        composable(
            route = "juz_detail/{juzNumber}",
            arguments = listOf(
                navArgument("juzNumber") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val juzNumber = backStackEntry.arguments?.getInt("juzNumber") ?: 1

            JuzDetailScreen(
                navController = navController,
                juzNumber = juzNumber
            )
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
