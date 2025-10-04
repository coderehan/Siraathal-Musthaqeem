package com.islamicapp.data.hijricalendar.source

import com.islamicapp.domain.hijricalendar.model.HijriCalendar

interface HijriCalendarDataSource {

    suspend fun getHijriCalendarDate(): HijriCalendar

}