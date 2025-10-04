package com.islamicapp.domain.hijricalendar.repository

import com.islamicapp.domain.hijricalendar.model.HijriCalendar

interface HijriCalendarRepository {

    suspend fun getHijriCalendarDate(): HijriCalendar

}