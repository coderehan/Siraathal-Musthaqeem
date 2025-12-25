package com.islamicapp.siraathalmusthaqeem.utils

object AsrSchoolMapper {

    const val SHAFI_STANDARD = 0
    const val HANAFI = 1

    fun getDefaultSchoolForCountry(countryCode: String?): Int {
        return when (countryCode?.uppercase()) {
            // Hanafi-majority
            "PK", "BD", "AF", "IN", "TR", "AZ", "KZ", "UZ", "KG", "TJ", "TM",
            "RU", "UA", "BY", "AL", "BA", "XK", "MK", "CN", "IQ", "SY", "JO",
            "PS", "LB", "EG" -> HANAFI

            // Shafi-majority
            else -> SHAFI_STANDARD
        }
    }

    fun getSchoolName(schoolId: Int): String {
        return when (schoolId) {
            HANAFI -> "Hanafi"
            SHAFI_STANDARD -> "Shafi"
            else -> "Shafi"
        }
    }
}