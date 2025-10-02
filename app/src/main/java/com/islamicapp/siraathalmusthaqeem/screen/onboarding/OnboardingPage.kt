package com.islamicapp.siraathalmusthaqeem.screen.onboarding

import com.islamicapp.siraathalmusthaqeem.R

data class OnboardingPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

val onboardingPages = listOf(
    OnboardingPage(
        imageRes = R.drawable.ic_android,
        title = "WELCOME TO SIRAATHAL MUSTHAQEEM",
        description = "Your complete islamic companion"
    ),
    OnboardingPage(
        imageRes = R.drawable.ic_android,
        title = "PRAYER TIMINGS & QIBLA",
        description = "Always stay connected with Salah"
    ),
    OnboardingPage(
        imageRes = R.drawable.ic_android,
        title = "QURAN & DUAS",
        description = "Read, listen, and reflect"
    ),
    OnboardingPage(
        imageRes = R.drawable.ic_android,
        title = "DAILY HADITH & DHIKR",
        description = "Build consistency in ibadah"
    ),
    OnboardingPage(
        imageRes = R.drawable.ic_android,
        title = "STAY INSPIRED",
        description = "Beautiful stories & names of Allah"
    )
)
