package com.islamicapp.siraathalmusthaqeem.di

import com.islamicapp.siraathalmusthaqeem.screen.allah99names.Allah99NamesViewModel
import com.islamicapp.siraathalmusthaqeem.screen.hadith.HadithViewModel
import com.islamicapp.siraathalmusthaqeem.screen.hijricalendar.HijriCalendarViewModel
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

}