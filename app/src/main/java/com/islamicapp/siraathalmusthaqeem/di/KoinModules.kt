package com.islamicapp.siraathalmusthaqeem.di

import com.islamicapp.siraathalmusthaqeem.screen.allah99names.Allah99NamesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// As 'ViewModel' comes under 'App Module', it should be declared in 'App Module' level
val appModule = module {
    viewModel { Allah99NamesViewModel(get()) }

}