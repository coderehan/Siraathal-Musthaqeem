package com.islamicapp.data.di

import com.islamicapp.core.Constants
import com.islamicapp.data.allah99names.source.Allah99NamesDataSource
import com.islamicapp.data.allah99names.source.Allah99NamesDataSourceImpl
import com.islamicapp.data.allah99names.source.network.Allah99NamesApi
import com.islamicapp.data.allah99names.source.network.Allah99NamesNetworkDataSource
import com.islamicapp.data.hijricalendar.source.HijriCalendarDataSource
import com.islamicapp.data.hijricalendar.source.HijriCalendarDataSourceImpl
import com.islamicapp.data.hijricalendar.source.network.HijriCalendarApi
import com.islamicapp.data.hijricalendar.source.network.HijriCalendarNetworkDataSource
import com.islamicapp.domain.allah99names.repository.Allah99NamesRepository
import com.islamicapp.domain.hijricalendar.repository.HijriCalendarRepository
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
const val SAHIH_AL_BUKHARI_RETROFIT = "SAHIH_AL_BUKHARI_RETROFIT"
const val SAHIH_MUSLIM_RETROFIT = "SAHIH_MUSLIM_RETROFIT"
const val SUNAN_AN_NASAI_RETROFIT = "SUNAN_AN_NASAI_RETROFIT"
const val SUNAN_ABU_DAWUD_RETROFIT = "SUNAN_ABU_DAWUD_RETROFIT"
const val JAMIAT_TIRMIDHI_RETROFIT = "JAMIAT_TIRMIDHI_RETROFIT"
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

    // SAHIH_AL_BUKHARI_RETROFIT instance
    single(named(SAHIH_AL_BUKHARI_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.SAHIH_AL_BUKHARI_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // SAHIH_MUSLIM_RETROFIT instance
    single(named(SAHIH_MUSLIM_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.SAHIH_MUSLIM_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // SUNAN_AN_NASAI_RETROFIT instance
    single(named(SUNAN_AN_NASAI_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.SUNAN_AN_NASAI_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // SUNAN_ABU_DAWUD_RETROFIT instance
    single(named(SUNAN_ABU_DAWUD_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.SUNAN_ABU_DAWUD_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // JAMIAT_TIRMIDHI_RETROFIT instance
    single(named(JAMIAT_TIRMIDHI_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.JAMIAT_TIRMIDHI_BASE_URL)
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
//    single { get<Retrofit>(named(PRAYER_TIMES_RETROFIT)).create(PrayerTimesApi::class.java) }
    single { get<Retrofit>(named(ALLAH_99_NAMES_RETROFIT)).create(Allah99NamesApi::class.java) }
//    single { get<Retrofit>(named(SAHIH_AL_BUKHARI_RETROFIT)).create(HadithApi::class.java) }
//    single { get<Retrofit>(named(SAHIH_MUSLIM_RETROFIT)).create(HadithApi::class.java) }
//    single { get<Retrofit>(named(SUNAN_AN_NASAI_RETROFIT)).create(HadithApi::class.java) }
//    single { get<Retrofit>(named(SUNAN_ABU_DAWUD_RETROFIT)).create(HadithApi::class.java) }
//    single { get<Retrofit>(named(JAMIAT_TIRMIDHI_RETROFIT)).create(HadithApi::class.java) }
//    single { get<Retrofit>(named(QURAN_SURAH_BASE_RETROFIT)).create(QuranApi::class.java) }
//    single { get<Retrofit>(named(QURAN_JUZ_BASE_RETROFIT)).create(QuranApi::class.java) }
//    single { get<Retrofit>(named(QURAN_AUDIO_BASE_RETROFIT)).create(QuranApi::class.java) }
    single { get<Retrofit>(named(HIJRI_CALENDAR_BASE_RETROFIT)).create(HijriCalendarApi::class.java) }


    /**
     * Register Network Data Source (API → Mapper → Domain model).
     * - Provides data from API.
     */
//    factory { PrayerTimesNetworkDataSource(prayerApi = get()) }
    factory<Allah99NamesDataSource> {
        Allah99NamesNetworkDataSource(get())
    }
//    factory { HadithNetworkDataSource(hadithApi = get()) }
//    factory { QuranNetworkDataSource(quranApi = get()) }
    factory<HijriCalendarDataSource> {
        HijriCalendarNetworkDataSource(get())
    }


    /**
     * Register Repository implementation.
     * - Bridges data layer with domain layer.
     * - Domain layer only depends on repository interface, not data source.
     */
//    factory<PrayerTimesRepository> {
//        PrayerTimesRepositoryImpl(prayerTimesNetworkDataSource = get())
//    }
    factory<Allah99NamesRepository> {
        Allah99NamesDataSourceImpl(get())
    }
//    factory<HadithRepository> {
//        HadithRepositoryImpl(hadithNetworkDataSource = get())
//    }
//    factory<QuranRepository> {
//        QuranRepositoryImpl(quranNetworkDataSource = get())
//    }
    factory<HijriCalendarRepository> {
        HijriCalendarDataSourceImpl(get())
    }

}



