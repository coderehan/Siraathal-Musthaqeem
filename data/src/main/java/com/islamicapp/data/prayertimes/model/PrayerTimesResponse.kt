package com.islamicapp.data.prayertimes.model

data class PrayerTimesResponse(
    val code: Int,
    val status: String,
    val data: Data
) {
    data class Data(
        val timings: Timings,
        val date: Date,
        val meta: Meta
    )

    data class Timings(
        val Fajr: String,
        val Sunrise: String,
        val Dhuhr: String,
        val Asr: String,
        val Sunset: String,
        val Maghrib: String,
        val Isha: String,
        val Imsak: String,
        val Midnight: String,
        val Firstthird: String,
        val Lastthird: String
    )

    data class Date(
        val readable: String,
        val timestamp: Long,
        val hijri: Hijri,
        val gregorian: Gregorian
    )

    data class Gregorian(
        val date: String,
        val format: String,
        val day: String,
        val weekday: Weekday,
        val month: Month,
        val year: String,
        val designation: Designation,
        val lunarSighting: Boolean
    )

    data class Hijri(
        val date: String,
        val format: String,
        val day: String,
        val weekday: WeekdayX,
        val month: MonthHijri,
        val year: String,
        val designation: Designation,
        val holidays: List<String>,
        val adjustedHolidays: List<String>,
        val method: String
    )

    data class Designation(
        val abbreviated: String,
        val expanded: String
    )

    data class Weekday(val en: String)

    data class WeekdayX(val en: String, val ar: String)

    data class Month(val number: Int, val en: String)

    data class MonthHijri(val number: Int, val en: String, val ar: String, val days: Int)

    data class Meta(
        val latitude: Double,
        val longitude: Double,
        val timezone: String,
        val method: Method,
        val latitudeAdjustmentMethod: String,
        val midnightMode: String,
        val school: String,
        val offset: Offset
    )

    data class Method(
        val id: Int,
        val name: String,
        val params: Params,
        val location: Location
    )

    data class Params(val Fajr: Int, val Isha: Int)

    data class Location(val latitude: Double, val longitude: Double)

    data class Offset(
        val Imsak: Int,
        val Fajr: Int,
        val Sunrise: Int,
        val Dhuhr: Int,
        val Asr: Int,
        val Sunset: Int,
        val Maghrib: Int,
        val Isha: Int,
        val Midnight: Int
    )
}