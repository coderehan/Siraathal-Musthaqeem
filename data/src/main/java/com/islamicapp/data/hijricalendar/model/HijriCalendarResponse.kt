package com.islamicapp.data.hijricalendar.model

data class HijriCalendarResponse(
    val code: Int,
    val status: String,
    val data: Data
) {

    data class Data(
        val gregorian: Gregorian,
        val hijri: Hijri
    )

    data class Gregorian(
        val date: String,
        val day: String,
        val designation: Designation,
        val format: String,
        val lunarSighting: Boolean,
        val month: Month,
        val weekday: Weekday,
        val year: String
    )

    data class Hijri(
        val adjustedHolidays: List<String>,
        val date: String,
        val day: String,
        val designation: Designation,
        val format: String,
        val holidays: List<String>,
        val method: String,
        val month: MonthX,
        val weekday: WeekdayX,
        val year: String
    )

    data class Designation(
        val abbreviated: String,
        val expanded: String
    )

    data class Month(
        val en: String,
        val number: Int
    )

    data class MonthX(
        val ar: String,
        val days: Int,
        val en: String,
        val number: Int
    )

    data class Weekday(
        val en: String
    )

    data class WeekdayX(
        val ar: String,
        val en: String
    )
}
