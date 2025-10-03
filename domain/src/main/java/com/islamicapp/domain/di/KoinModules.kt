package com.islamicapp.domain.di

import com.islamicapp.domain.allah99names.usecase.GetAllah99NamesUseCase
import org.koin.dsl.module

// As 'UseCase' comes under 'Domain Module', it should come under 'Domain Module' level
val domainModule = module {
    factory { GetAllah99NamesUseCase(get()) }

}