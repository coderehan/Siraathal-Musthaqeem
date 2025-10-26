package com.islamicapp.siraathalmusthaqeem.screen.navigation

// Sealed class defining all navigation routes in the app
// Each object represents one screen/feature and provides a unique route string.
sealed class NavigationItem(val route: String) {

    data object Splash : NavigationItem("splash")
    data object Onboarding : NavigationItem("onboarding")
    data object Profile : NavigationItem("profile")
    data object Home : NavigationItem("home")
    data object PrayerTimes : NavigationItem("prayer_times")
    data object Qibla : NavigationItem("qibla")
    data object Azaan : NavigationItem("azaan")
    data object Allah99Names : NavigationItem("allah_99_names")
    data object Zakath : NavigationItem("zakath")
    data object Pilgrimage : NavigationItem("pilgrimage")
    data object Hadith : NavigationItem("hadith")
    data object Seerah : NavigationItem("seerah")
    data object Prophets : NavigationItem("prophets")
    data object Quran : NavigationItem("quran")
    data object Dua : NavigationItem("dua")
    data object Tasbeeh : NavigationItem("tasbeeh")
    data object Theme : NavigationItem("theme")

}