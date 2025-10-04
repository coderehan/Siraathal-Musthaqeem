package com.islamicapp.data.hijricalendar.mapper

import com.islamicapp.data.common.mapper.BaseMapper
import com.islamicapp.data.hijricalendar.model.HijriCalendarResponse
import com.islamicapp.domain.hijricalendar.model.HijriCalendar

class HijriCalendarMapper : BaseMapper<HijriCalendarResponse, HijriCalendar>() {

    override fun map(input: HijriCalendarResponse): HijriCalendar {
        val hijri = input.data.hijri
        val gregorian = input.data.gregorian

        return HijriCalendar(
            hijriDay = hijri.day,
            hijriMonth = hijri.month.en,
            hijriYear = hijri.year,
            englishDay = gregorian.day,
            englishMonth = gregorian.month.en,
            englishYear = gregorian.year,
            englishWeekday = gregorian.weekday.en
        )
    }
}
