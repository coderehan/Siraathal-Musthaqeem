package com.islamicapp.siraathalmusthaqeem.screen.onboarding

import com.islamicapp.siraathalmusthaqeem.R

data class OnboardingPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

val onboardingPages = listOf(
    OnboardingPage(
        imageRes = R.drawable.onboarding_1,
        title = "WELCOME TO SIRAATHAL MUSTHAQEEM",
        description = "Your complete islamic companion"
    ),
    OnboardingPage(
        imageRes = R.drawable.onboarding_1,
        title = "PRAYER TIMINGS & QIBLA",
        description = "Always stay connected with Salah"
    ),
    OnboardingPage(
        imageRes = R.drawable.onboarding_1,
        title = "QURAN & DUAS",
        description = "Read, listen, and reflect"
    ),
    OnboardingPage(
        imageRes = R.drawable.onboarding_1,
        title = "DAILY HADITH & DHIKR",
        description = "Build consistency in ibadah"
    ),
    OnboardingPage(
        imageRes = R.drawable.onboarding_1,
        title = "STAY INSPIRED",
        description = "Beautiful stories & names of Allah"
    )
)
