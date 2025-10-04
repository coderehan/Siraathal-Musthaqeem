package com.islamicapp.data.hijricalendar.source

import com.islamicapp.domain.hijricalendar.model.HijriCalendar
import com.islamicapp.domain.hijricalendar.repository.HijriCalendarRepository

class HijriCalendarDataSourceImpl(
    private val dataSource: HijriCalendarDataSource
) : HijriCalendarRepository {

    override suspend fun getHijriCalendarDate(): HijriCalendar {
        return dataSource.getHijriCalendarDate()
    }
}