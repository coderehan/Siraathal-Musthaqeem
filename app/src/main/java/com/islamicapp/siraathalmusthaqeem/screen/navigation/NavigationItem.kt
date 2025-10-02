package com.islamicapp.siraathalmusthaqeem.screen.navigation

// Sealed class to define all app navigation routes
sealed class NavigationItem(val route: String) {

    object Splash : NavigationItem("splash")
    object Onboarding : NavigationItem("onboarding")
    object Profile : NavigationItem("profile")
    object Home : NavigationItem("home")
    object PrayerTimes : NavigationItem("prayer_times")
    object Qibla : NavigationItem("qibla")
    object Masjid : NavigationItem("masjid")
    object Allah99Names : NavigationItem("allah_99_names")
    object Zakath : NavigationItem("zakath")
    object Pilgrimage : NavigationItem("pilgrimage")
    object Hadith : NavigationItem("hadith")
    object Seerah : NavigationItem("seerah")
    object Prophets : NavigationItem("prophets")
    object Quran : NavigationItem("quran")
    object Dua : NavigationItem("dua")
    object Tasbeeh : NavigationItem("tasbeeh")

}