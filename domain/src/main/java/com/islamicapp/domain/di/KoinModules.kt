package com.islamicapp.domain.di

import com.islamicapp.domain.allah99names.usecase.Allah99NamesUseCase
import com.islamicapp.domain.dailycontent.usecase.DailyDuaUseCase
import com.islamicapp.domain.dailycontent.usecase.DailyHadithUseCase
import com.islamicapp.domain.duas.emotionsduas.usecase.EmotionsDuasUseCase
import com.islamicapp.domain.duas.masnoonduas.usecase.MasnoonDuasUseCase
import com.islamicapp.domain.duas.prophetsduas.usecase.ProphetsDuasUseCase
import com.islamicapp.domain.duas.quranicduas.usecase.QuranicDuasUseCase
import com.islamicapp.domain.duas.rabbanaduas.usecase.RabbanaDuasUseCase
import com.islamicapp.domain.hadith.usecase.HadithUseCase
import com.islamicapp.domain.pilgrimage.usecase.PilgrimageUseCase
import com.islamicapp.domain.prayertimes.usecase.PrayerTimesUseCase
import com.islamicapp.domain.prophetsstories.usecase.ProphetsStoriesUseCase
import com.islamicapp.domain.seerah.usecase.SeerahUseCase
import org.koin.dsl.module

// As 'UseCase' comes under 'Domain Module', it should come under 'Domain Module' level
val domainModule = module {
    /**
     * Factory binding for GetAllah99NamesUseCase
     *
     * - Why factory? → Each time it's requested, a new instance is created.
     *   Use cases are lightweight and stateless, so factory is fine.
     * - Depends on: Allah99NamesRepository (provided by data module).
     */
    factory { Allah99NamesUseCase(get()) }
    factory { HadithUseCase(get()) }
    factory { PrayerTimesUseCase(get()) }
    factory { ProphetsStoriesUseCase(get()) }
    factory { RabbanaDuasUseCase(get()) }
    factory { EmotionsDuasUseCase(get()) }
    factory { ProphetsDuasUseCase(get()) }
    factory { QuranicDuasUseCase(get()) }
    factory { MasnoonDuasUseCase(get()) }
    factory { SeerahUseCase(get()) }
    factory { PilgrimageUseCase(get()) }
    factory { DailyHadithUseCase(get()) }
    factory { DailyDuaUseCase(get()) }

}