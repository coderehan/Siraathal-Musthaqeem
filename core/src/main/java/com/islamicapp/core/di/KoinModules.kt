package com.islamicapp.core.di

import com.islamicapp.core.location.LocationProvider
import com.islamicapp.core.location.LocationProviderImpl
import org.koin.dsl.module


val coreModule = module {
    single<LocationProvider> { LocationProviderImpl(get()) }
}