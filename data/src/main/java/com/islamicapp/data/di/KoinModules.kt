package com.islamicapp.data.di

import com.islamicapp.core.utils.Constants
import com.islamicapp.data.allah99names.source.Allah99NamesDataSource
import com.islamicapp.data.allah99names.source.Allah99NamesDataSourceImpl
import com.islamicapp.data.allah99names.source.network.Allah99NamesApi
import com.islamicapp.data.allah99names.source.network.Allah99NamesNetworkDataSource
import com.islamicapp.data.duas.emotionsduas.local.EmotionsDuasDataSource
import com.islamicapp.data.duas.emotionsduas.local.EmotionsDuasDataSourceImpl
import com.islamicapp.data.duas.emotionsduas.repository.EmotionsDuasRepositoryImpl
import com.islamicapp.data.duas.prophetsduas.local.ProphetsDuasDataSource
import com.islamicapp.data.duas.prophetsduas.local.ProphetsDuasDataSourceImpl
import com.islamicapp.data.duas.prophetsduas.repository.ProphetsDuasRepositoryImpl
import com.islamicapp.data.duas.quranicduas.local.QuranicDuasDataSource
import com.islamicapp.data.duas.quranicduas.local.QuranicDuasDataSourceImpl
import com.islamicapp.data.duas.quranicduas.repository.QuranicDuasRepositoryImpl
import com.islamicapp.data.duas.rabbanaduas.repository.RabbanaDuasRepositoryImpl
import com.islamicapp.data.duas.rabbanaduas.source.local.RabbanaDuasDataSource
import com.islamicapp.data.duas.rabbanaduas.source.local.RabbanaDuasDataSourceImpl
import com.islamicapp.data.hadith.mapper.HadithMapper
import com.islamicapp.data.hadith.source.HadithDataSource
import com.islamicapp.data.hadith.source.HadithDataSourceImpl
import com.islamicapp.data.hadith.source.network.HadithApi
import com.islamicapp.data.hadith.source.network.HadithNetworkDataSource
import com.islamicapp.data.hijricalendar.source.HijriCalendarDataSource
import com.islamicapp.data.hijricalendar.source.HijriCalendarDataSourceImpl
import com.islamicapp.data.hijricalendar.source.network.HijriCalendarApi
import com.islamicapp.data.hijricalendar.source.network.HijriCalendarNetworkDataSource
import com.islamicapp.data.prayertimes.source.PrayerTimesDataSource
import com.islamicapp.data.prayertimes.source.PrayerTimesDataSourceImpl
import com.islamicapp.data.prayertimes.source.network.PrayerTimesApi
import com.islamicapp.data.prayertimes.source.network.PrayerTimesNetworkDataSource
import com.islamicapp.data.prophetsstories.repository.ProphetsStoriesRepositoryImpl
import com.islamicapp.data.prophetsstories.source.local.ProphetsStoriesDataSource
import com.islamicapp.data.prophetsstories.source.local.ProphetsStoriesDataSourceImpl
import com.islamicapp.data.quran.mapper.QuranMapper
import com.islamicapp.data.quran.source.QuranDataSource
import com.islamicapp.data.quran.source.QuranDataSourceImpl
import com.islamicapp.data.quran.source.network.QuranApi
import com.islamicapp.data.quran.source.network.QuranNetworkDataSource
import com.islamicapp.domain.allah99names.repository.Allah99NamesRepository
import com.islamicapp.domain.duas.emotionsduas.repository.EmotionsDuasRepository
import com.islamicapp.domain.duas.prophetsduas.repository.ProphetsDuasRepository
import com.islamicapp.domain.duas.quranicduas.repository.QuranicDuasRepository
import com.islamicapp.domain.duas.rabbanaduas.repository.RabbanaDuasRepository
import com.islamicapp.domain.hadith.repository.HadithRepository
import com.islamicapp.domain.hijricalendar.repository.HijriCalendarRepository
import com.islamicapp.domain.prayertimes.repository.PrayerTimesRepository
import com.islamicapp.domain.prophetsstories.repository.ProphetsStoriesRepository
import com.islamicapp.domain.quran.repository.QuranRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Constants used to distinguish different Retrofit instances.
 * Each feature has its own base URL and Retrofit binding.
 */
const val PRAYER_TIMES_RETROFIT = "PRAYER_TIMES_RETROFIT"
const val QIBLA_RETROFIT = "QIBLA_RETROFIT"
const val ALLAH_99_NAMES_RETROFIT = "ALLAH_99_NAMES_RETROFIT"
const val HADITH_RETROFIT = "HADITH_RETROFIT"
const val QURAN_SURAH_BASE_RETROFIT = "QURAN_SURAH_BASE_RETROFIT"
const val QURAN_JUZ_BASE_RETROFIT = "QURAN_JUZ_BASE_RETROFIT"
const val QURAN_AUDIO_BASE_RETROFIT = "QURAN_AUDIO_BASE_RETROFIT"
const val HIJRI_CALENDAR_BASE_RETROFIT = "HIJRI_CALENDAR_BASE_RETROFIT"

// As 'API interfaces', 'Network Data Source', 'Data Source Impl' comes under 'Data Module', it should be declared in 'Data Module' level
val dataModule = module {

    /**
     * Provide Retrofit instances for each feature API.
     * - Named bindings allow multiple Retrofit clients in the app.
     * - Each Retrofit uses its respective BASE_URL from Constants.
     */
// PRAYER_TIMES_RETROFIT instance
    single(named(PRAYER_TIMES_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.PRAYER_TIMES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // QIBLA_RETROFIT instance
    single(named(QIBLA_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.QIBLA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ALLAH_99_NAMES_RETROFIT instance
    single(named(ALLAH_99_NAMES_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.ALLAH_99_NAMES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // HADITH_RETROFIT instance
    single(named(HADITH_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.HADITH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // QURAN_SURAH_BASE_RETROFIT instance
    single(named(QURAN_SURAH_BASE_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.QURAN_SURAH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // QURAN_JUZ_BASE_RETROFIT instance
    single(named(QURAN_JUZ_BASE_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.QURAN_JUZ_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // QURAN_AUDIO_BASE_RETROFIT instance
    single(named(QURAN_AUDIO_BASE_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.QURAN_AUDIO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // HIJRI_CALENDAR_BASE_RETROFIT instance
    single(named(HIJRI_CALENDAR_BASE_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.HIJRI_CALENDAR_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Register API interfaces using respective Retrofit clients.
     * Example: Allah99NamesApi uses ALLAH_99_NAMES_RETROFIT instance.
     */
    single { get<Retrofit>(named(PRAYER_TIMES_RETROFIT)).create(PrayerTimesApi::class.java) }
    single { get<Retrofit>(named(ALLAH_99_NAMES_RETROFIT)).create(Allah99NamesApi::class.java) }
    single { get<Retrofit>(named(HADITH_RETROFIT)).create(HadithApi::class.java) }
    single { get<Retrofit>(named(QURAN_SURAH_BASE_RETROFIT)).create(QuranApi::class.java) }
    single { get<Retrofit>(named(QURAN_JUZ_BASE_RETROFIT)).create(QuranApi::class.java) }
//    single { get<Retrofit>(named(QURAN_AUDIO_BASE_RETROFIT)).create(QuranApi::class.java) }
    single { get<Retrofit>(named(HIJRI_CALENDAR_BASE_RETROFIT)).create(HijriCalendarApi::class.java) }

    // Register mapper
    factory { HadithMapper() }
    factory { QuranMapper() }

    /**
     * Register Network Data Source (API → Mapper → Domain model).
     * - Provides data from API.
     */
    factory<PrayerTimesDataSource> {
        PrayerTimesNetworkDataSource(get())
    }
    factory<Allah99NamesDataSource> {
        Allah99NamesNetworkDataSource(get())
    }
    factory<HadithDataSource> {
        HadithNetworkDataSource(get(), get())
    }
    factory<QuranDataSource> {
        QuranNetworkDataSource(get(), get())
    }
    factory<HijriCalendarDataSource> {
        HijriCalendarNetworkDataSource(get())
    }
    factory<ProphetsStoriesDataSource> {
        ProphetsStoriesDataSourceImpl(get())
    }
    factory<RabbanaDuasDataSource> {
        RabbanaDuasDataSourceImpl(get())
    }
    factory<EmotionsDuasDataSource> {
        EmotionsDuasDataSourceImpl(get())
    }
    factory<ProphetsDuasDataSource> {
        ProphetsDuasDataSourceImpl(get())
    }
    factory<QuranicDuasDataSource> {
        QuranicDuasDataSourceImpl(get())
    }


    /**
     * Register Repository implementation.
     * - Bridges data layer with domain layer.
     * - Domain layer only depends on repository interface, not data source.
     */
    factory<PrayerTimesRepository> {
        PrayerTimesDataSourceImpl(get())
    }
    factory<Allah99NamesRepository> {
        Allah99NamesDataSourceImpl(get())
    }
    factory<HadithRepository> {
        HadithDataSourceImpl(get())
    }
    factory<QuranRepository> {
        QuranDataSourceImpl(get())
    }
    factory<HijriCalendarRepository> {
        HijriCalendarDataSourceImpl(get())
    }
    factory<ProphetsStoriesRepository> {
        ProphetsStoriesRepositoryImpl(get())
    }
    factory<RabbanaDuasRepository> {
        RabbanaDuasRepositoryImpl(get())
    }
    factory<EmotionsDuasRepository> {
        EmotionsDuasRepositoryImpl(get())
    }
    factory<ProphetsDuasRepository> {
        ProphetsDuasRepositoryImpl(get())
    }
    factory<QuranicDuasRepository> {
        QuranicDuasRepositoryImpl(get())
    }

}



