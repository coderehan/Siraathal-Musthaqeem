package com.islamicapp.siraathalmusthaqeem.di

import com.islamicapp.siraathalmusthaqeem.utils.Constants


import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

fun makeApiModule() = module {

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

    // Provide all API interfaces
//    single { get<Retrofit>(named(PRAYER_TIMES_RETROFIT)).create(PrayerTimesApi::class.java) }
//    single { get<Retrofit>(named(QIBLA_RETROFIT)).create(QiblaApi::class.java) }
//    single { get<Retrofit>(named(ALLAH_99_NAMES_RETROFIT)).create(Allah99NamesApi::class.java) }
//    single { get<Retrofit>(named(SAHIH_AL_BUKHARI_RETROFIT)).create(HadithApi::class.java) }
//    single { get<Retrofit>(named(SAHIH_MUSLIM_RETROFIT)).create(HadithApi::class.java) }
//    single { get<Retrofit>(named(SUNAN_AN_NASAI_RETROFIT)).create(HadithApi::class.java) }
//    single { get<Retrofit>(named(SUNAN_ABU_DAWUD_RETROFIT)).create(HadithApi::class.java) }
//    single { get<Retrofit>(named(JAMIAT_TIRMIDHI_RETROFIT)).create(HadithApi::class.java) }
//    single { get<Retrofit>(named(QURAN_SURAH_BASE_RETROFIT)).create(QuranApi::class.java) }
//    single { get<Retrofit>(named(QURAN_JUZ_BASE_RETROFIT)).create(QuranApi::class.java) }
//    single { get<Retrofit>(named(QURAN_AUDIO_BASE_RETROFIT)).create(QuranApi::class.java) }
//    single { get<Retrofit>(named(HIJRI_CALENDAR_BASE_RETROFIT)).create(HijriCalendarApi::class.java) }
}
}

fun makeDataSourceModule() = module {
//    factory { PrayerNetworkDataSource(prayerApi = get()) }
//    factory { QuranNetworkDataSource(quranApi = get()) }
//    factory { DuaNetworkDataSource(duaApi = get()) }
//    factory { TasbeehNetworkDataSource(tasbeehApi = get()) }
}

fun makeRepositoryModule() = module {
//    factory<PrayerRepository> {
//        PrayerRepositoryImpl(prayerNetworkDataSource = get())
//    }
//    factory<QuranRepository> {
//        QuranRepositoryImpl(quranNetworkDataSource = get())
//    }
//    factory<DuaRepository> {
//        DuaRepositoryImpl(duaNetworkDataSource = get())
//    }
//    factory<TasbeehRepository> {
//        TasbeehRepositoryImpl(tasbeehNetworkDataSource = get())
//    }
}

