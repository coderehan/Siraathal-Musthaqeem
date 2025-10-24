package com.islamicapp.data.quran.mapper

import com.islamicapp.data.quran.model.juz.JuzDetailResponse
import com.islamicapp.data.quran.model.surah.SurahDetailResponse
import com.islamicapp.data.quran.model.surah.SurahListResponse
import com.islamicapp.domain.quran.model.Juz
import com.islamicapp.domain.quran.model.Surah

class QuranMapper {

    fun mapSurahList(input: SurahListResponse): List<Surah> {
        return input.data.map { item ->
            Surah(
                surahNumber = item.number,
                surahNameArabic = item.name,
                surahNameEnglish = item.englishName,
                englishNameTranslation = item.englishNameTranslation,
                revelationType = item.revelationType,
                numberOfAyahs = item.numberOfAyahs,
                ayahText = "",
                numberInSurah = 0,
                hasSajda = false
            )
        }
    }

    fun mapSurahDetail(input: SurahDetailResponse): List<Surah> {
        val data = input.data
        val distinctAyahs = data.ayahs.distinctBy { it.number }
        return distinctAyahs.map { ayah ->
            Surah(
                surahNumber = data.number,
                surahNameArabic = data.name,
                surahNameEnglish = data.englishName,
                englishNameTranslation = data.englishNameTranslation,
                revelationType = data.revelationType,
                numberOfAyahs = data.numberOfAyahs,
                ayahText = ayah.text.trim(),
                numberInSurah = ayah.numberInSurah,
                hasSajda = ayah.sajda?.hasSajda ?: false,
                sajdaRecommended = ayah.sajda?.recommended,
                sajdaObligatory = ayah.sajda?.obligatory
            )
        }
    }

    fun mapJuzDetail(input: JuzDetailResponse): List<Juz> {
        val data = input.data
        val distinctAyahs = data.ayahs.distinctBy { it.number }.sortedBy { it.number }
        return distinctAyahs.map { ayah ->
            Juz(
                surahNumber = ayah.surah.number,
                surahNameArabic = ayah.surah.name,
                surahNameEnglish = ayah.surah.englishName,
                englishNameTranslation = ayah.surah.englishNameTranslation,
                revelationType = ayah.surah.revelationType,
                numberOfAyahs = ayah.surah.numberOfAyahs,
                ayahText = ayah.text.trim(),
                numberInSurah = ayah.numberInSurah
            )
        }
    }

}