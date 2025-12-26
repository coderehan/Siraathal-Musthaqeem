plugins {
    // Android Application Plugin
    alias(libs.plugins.android.application)

    // Kotlin Plugin for Android
    alias(libs.plugins.kotlin.android)

    // Jetpack Compose support
    alias(libs.plugins.kotlin.compose)

    // KSP (Kotlin Symbol Processing) - faster than kapt
    alias(libs.plugins.ksp)

    // Google Services for Firebase Crashlytics
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)

}

android {
    namespace = "com.islamicapp.siraathalmusthaqeem"
    compileSdk = 36

    defaultConfig {
        // Only :app module should have applicationId
        applicationId = "com.islamicapp.siraathalmusthaqeem"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // JUnit test runner
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            // ProGuard settings
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // Java 11 compatibility
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    buildFeatures {
        // Enables Jetpack Compose
        compose = true
    }

    packaging {
        resources {
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }

}

dependencies {
    // App Module can depend on all three i.e. core, domain, data modules
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))

    // Core KTX extensions
    implementation(libs.androidx.core.ktx)

    // Lifecycle Runtime (observe lifecycle state in Kotlin)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Activity Compose support
    implementation(libs.androidx.activity.compose)

    // Compose BOM (Bill of Materials to align Compose versions)
    implementation(platform(libs.androidx.compose.bom))

    // Compose Material Icons
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)

    // Jetpack Compose UI & Graphics
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)

    // Preview and Tooling support
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // Material 3 UI Components
    implementation(libs.androidx.material3)

    // JUnit for unit testing
    testImplementation(libs.junit)

    // AndroidX Test - JUnit & Espresso for UI testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose UI Testing
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Koin for Dependency Injection
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Accompanist Permissions (Compose)
    implementation(libs.accompanist.permissions)

    // Gson for JSON parsing
    implementation(libs.gson)

    // Retrofit & OkHttp for networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Kotlin Coroutines for async programming
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // ViewModel, LiveData, StateFlow, Lifecycle runtime (including Compose support)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.runtime.compose)

    // Google Maps & Location Services
    implementation(libs.maps)
    implementation(libs.location)

    // WorkManager for background tasks
    implementation(libs.workManager)

    // Jetpack Navigation for Compose
    implementation(libs.navigation.compose)

    // Jetpack DataStore (SharedPreferences replacement)
    implementation(libs.datastore.preferences)

    // ExoPlayer for video and audio playback
    implementation(libs.exoplayer.core)
    implementation(libs.exoplayer.ui)

    // Accompanist Pager & Indicators
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    // Spinkit Progressbar
    implementation(libs.spinkit)

    // Firebase BOM
    implementation(platform(libs.firebase.bom))

    // Firebase Analytics (The BOM will decide the version)
    implementation(libs.firebase.analytics)

    // Firebase Crashlytics (The BOM will decide the version)
    implementation(libs.firebase.crashlytics)
}
