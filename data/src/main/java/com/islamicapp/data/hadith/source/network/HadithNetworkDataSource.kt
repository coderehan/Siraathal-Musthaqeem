package com.islamicapp.data.hadith.source.network

import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.data.hadith.mapper.HadithMapper
import com.islamicapp.data.hadith.source.HadithDataSource
import com.islamicapp.domain.hadith.model.Hadith

class HadithNetworkDataSource(
    private val api: HadithApi,
    private val mapper: HadithMapper
) : HadithDataSource {

    override suspend fun getHadiths(collection: HadithCollectionType): List<Hadith> {
        return when (collection) {
            HadithCollectionType.BUKHARI -> mapper.map(api.getSahihAlBukhariHadith())
            HadithCollectionType.MUSLIM -> mapper.map(api.getSahihMuslimHadith())
            HadithCollectionType.NASAI -> mapper.map(api.getSunanAnNasaiHadith())
            HadithCollectionType.ABU_DAWUD -> mapper.map(api.getSunanAbuDawudHadith())
            HadithCollectionType.TIRMIDHI -> mapper.map(api.getJamiatTirmidhiHadith())
        }
    }
}
