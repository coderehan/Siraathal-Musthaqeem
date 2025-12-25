package com.islamicapp.siraathalmusthaqeem.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.siraathalmusthaqeem.screen.aboutapp.AboutAppScreen
import com.islamicapp.siraathalmusthaqeem.screen.allah99names.Allah99NamesScreen
import com.islamicapp.siraathalmusthaqeem.screen.azaan.AzaanScreen
import com.islamicapp.siraathalmusthaqeem.screen.dua.DuaScreen
import com.islamicapp.siraathalmusthaqeem.screen.dua.emotionsduas.EmotionsDuasDetailScreen
import com.islamicapp.siraathalmusthaqeem.screen.dua.quranicduas.QuranicDuasDetailScreen
import com.islamicapp.siraathalmusthaqeem.screen.hadith.HadithScreen
import com.islamicapp.siraathalmusthaqeem.screen.hadithdetail.HadithDetailScreen
import com.islamicapp.siraathalmusthaqeem.screen.home.HomeScreen
import com.islamicapp.siraathalmusthaqeem.screen.splash.SplashScreen
import com.islamicapp.siraathalmusthaqeem.screen.onboarding.OnboardingScreen
import com.islamicapp.siraathalmusthaqeem.screen.pilgrimage.PilgrimageScreen
import com.islamicapp.siraathalmusthaqeem.screen.prayertimes.PrayerSettingsScreen
import com.islamicapp.siraathalmusthaqeem.screen.prayertimes.PrayerTimesScreen
import com.islamicapp.siraathalmusthaqeem.screen.profile.ProfileScreen
import com.islamicapp.siraathalmusthaqeem.screen.prophetsstories.ProphetsStoriesDetailScreen
import com.islamicapp.siraathalmusthaqeem.screen.prophetsstories.ProphetsStoriesListScreen
import com.islamicapp.siraathalmusthaqeem.screen.quran.QuranScreen
import com.islamicapp.siraathalmusthaqeem.screen.seerah.SeerahListScreen
import com.islamicapp.siraathalmusthaqeem.screen.seerah.SeerahDetailScreen
import com.islamicapp.siraathalmusthaqeem.screen.tasbeeh.TasbeehScreen
import com.islamicapp.siraathalmusthaqeem.screen.zakath.ZakathScreen
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import com.islamicapp.siraathalmusthaqeem.screen.qibla.QiblaScreen
import com.islamicapp.siraathalmusthaqeem.screen.quran.QuranPdfReaderScreen
import com.islamicapp.siraathalmusthaqeem.screen.theme.ThemeSelectorWrapper
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

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

        // Prayer Times Screen
        composable(NavigationItem.PrayerTimes.route) {
            PrayerTimesScreen(navController = navController)
        }

        // Prayer Settings Screen
        composable(
            route = "prayer_settings/{method}/{school}/{lat}/{long}/{timezone}",
            arguments = listOf(
                navArgument("method") { type = NavType.IntType },
                navArgument("school") { type = NavType.IntType },
                navArgument("lat") { type = NavType.FloatType },
                navArgument("long") { type = NavType.FloatType },
                navArgument("timezone") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedTimezone = backStackEntry.arguments?.getString("timezone") ?: ""
            val decodedTimezone = URLDecoder.decode(encodedTimezone, StandardCharsets.UTF_8.toString())

            PrayerSettingsScreen(
                navController = navController,
                currentMethod = backStackEntry.arguments?.getInt("method") ?: 3,
                currentSchool = backStackEntry.arguments?.getInt("school") ?: 0,
                latitude = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0,
                longitude = backStackEntry.arguments?.getFloat("long")?.toDouble() ?: 0.0,
            )
        }

        // Qibla
        composable(NavigationItem.Qibla.route) {
            QiblaScreen(navController = navController)
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

        // Seerah List Screen
        composable(NavigationItem.Seerah.route) {
            SeerahListScreen(navController = navController)
        }

        // Seerah Detail Screen
        composable(
            route = "seerah_detail/{chapterTitle}/{fileName}",
            arguments = listOf(
                navArgument("chapterTitle") { type = NavType.StringType },
                navArgument("fileName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chapterTitle = backStackEntry.arguments?.getString("chapterTitle") ?: ""
            val fileName = backStackEntry.arguments?.getString("fileName") ?: ""

            SeerahDetailScreen(
                navController = navController,
                chapterTitle = chapterTitle,
                fileName = fileName
            )
        }

        // Prophets
        composable(NavigationItem.Prophets.route) {
            ProphetsStoriesListScreen(navController = navController)
        }

        // Prophets Stories Detail
        composable(
            route = "prophet_detail/{prophetName}/{fileName}",
            arguments = listOf(
                navArgument("prophetName") { type = NavType.StringType },
                navArgument("fileName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val prophetName = backStackEntry.arguments?.getString("prophetName") ?: ""
            val fileName = backStackEntry.arguments?.getString("fileName") ?: ""

            ProphetsStoriesDetailScreen(
                navController = navController,
                prophetName = prophetName,
                fileName = fileName
            )
        }


        // Quran
        composable(NavigationItem.Quran.route) {
            QuranScreen(navController = navController)
        }

        // PDF Viewer Screen with Surah/Juz support
        composable(
            route = "quran_pdf_reader?surahNumber={surahNumber}&juzNumber={juzNumber}",
            arguments = listOf(
                navArgument("surahNumber") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("juzNumber") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val surahNumber = backStackEntry.arguments?.getInt("surahNumber")?.takeIf { it != -1 }
            val juzNumber = backStackEntry.arguments?.getInt("juzNumber")?.takeIf { it != -1 }

            QuranPdfReaderScreen(
                navController = navController,
                surahNumber = surahNumber,
                juzNumber = juzNumber
            )
        }

        // Dua
        composable(NavigationItem.Dua.route) {
            DuaScreen(navController = navController)
        }

        // Emotions Duas Detail
        composable(
            route = "emotion_detail/{emotionName}/{fileName}",
            arguments = listOf(
                navArgument("emotionName") { type = NavType.StringType },
                navArgument("fileName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val emotionName = backStackEntry.arguments?.getString("emotionName") ?: ""
            val fileName = backStackEntry.arguments?.getString("fileName") ?: ""

            EmotionsDuasDetailScreen(
                navController = navController,
                emotionName = emotionName,
                fileName = fileName
            )
        }

        // Quranic Duas Detail
        composable(
            route = "quranic_dua_detail/{categoryName}/{fileName}",
            arguments = listOf(
                navArgument("categoryName") { type = NavType.StringType },
                navArgument("fileName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val fileName = backStackEntry.arguments?.getString("fileName") ?: ""

            QuranicDuasDetailScreen(
                navController = navController,
                categoryName = categoryName,
                fileName = fileName
            )
        }

        // Tasbeeh
        composable(NavigationItem.Tasbeeh.route) {
            TasbeehScreen(navController = navController)
        }

        composable("azaan") {
            AzaanScreen(navController = navController)
        }

        // About App
        composable(route = "about") {
            AboutAppScreen(navController = navController)
        }
    }
}
