package com.islamicapp.domain.hijricalendar.usecase

import com.islamicapp.domain.hijricalendar.model.HijriCalendar
import com.islamicapp.domain.hijricalendar.repository.HijriCalendarRepository

class HijriCalendarUseCase(
    private val repository: HijriCalendarRepository
) {
    suspend operator fun invoke(): HijriCalendar {
        return repository.getHijriCalendarDate()
    }
}