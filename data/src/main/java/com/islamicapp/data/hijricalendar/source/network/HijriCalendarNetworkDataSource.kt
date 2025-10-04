package com.islamicapp.data.hijricalendar.source.network

import com.islamicapp.data.hijricalendar.mapper.HijriCalendarMapper
import com.islamicapp.data.hijricalendar.source.HijriCalendarDataSource
import com.islamicapp.domain.hijricalendar.model.HijriCalendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HijriCalendarNetworkDataSource(
    private val api: HijriCalendarApi
) : HijriCalendarDataSource {

    override suspend fun getHijriCalendarDate(): HijriCalendar {
        val todayDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        return api.getHijriCalendarDate(todayDate).run(HijriCalendarMapper()::map)
    }
}
