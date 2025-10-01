// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Android Application Plugin
    alias(libs.plugins.android.application) apply false

    // Kotlin Plugin for Android
    alias(libs.plugins.kotlin.android) apply false

    // Kotlin Compose Plugin
    alias(libs.plugins.kotlin.compose) apply false

    // Kotlin Symbol Processing (KSP) - replaces kapt for Room and others
    alias(libs.plugins.ksp) apply false

}
