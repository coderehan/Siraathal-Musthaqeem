package com.islamicapp.siraathalmusthaqeem.di

import com.islamicapp.siraathalmusthaqeem.screen.allah99names.Allah99NamesViewModel
import com.islamicapp.siraathalmusthaqeem.screen.hadith.HadithViewModel
import com.islamicapp.siraathalmusthaqeem.screen.hijricalendar.HijriCalendarViewModel
import com.islamicapp.siraathalmusthaqeem.screen.prayertimes.PrayerTimesViewModel
import com.islamicapp.siraathalmusthaqeem.screen.prophetsstories.ProphetsStoriesViewModel
import com.islamicapp.siraathalmusthaqeem.screen.quran.juz.JuzViewModel
import com.islamicapp.siraathalmusthaqeem.screen.quran.surah.SurahViewModel
import com.islamicapp.siraathalmusthaqeem.screen.tasbeeh.TasbeehViewModel
import com.islamicapp.siraathalmusthaqeem.screen.theme.ThemeViewModel
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

// As 'ViewModel' comes under 'App Module', it should be declared in 'App Module' level
val appModule = module {
    /**
     * Register ViewModels.
     * - ViewModel depends on UseCase (domain layer).
     * - Koin injects the GetAllah99NamesUseCase automatically.
     */
    viewModel { Allah99NamesViewModel(get()) }
    viewModel { HijriCalendarViewModel(get()) }
    viewModel { HadithViewModel(get()) }
    viewModel { SurahViewModel(get()) }
    viewModel { JuzViewModel(get()) }
    viewModel { PrayerTimesViewModel(get()) }
    viewModel { TasbeehViewModel(get()) }
    viewModel { ProphetsStoriesViewModel(get()) }
    viewModel { ThemeViewModel(get()) }
    single { PreferencesManager(get()) }

}