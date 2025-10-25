package com.islamicapp.domain.prayertimes.model

data class PrayerTimes(
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val sunset: String,
    val maghrib: String,
    val isha: String,
    val tahajjud: String,
    val gregorianDate: String,
    val hijriDay: String,
    val hijriMonth: String,
    val hijriYear: String
)