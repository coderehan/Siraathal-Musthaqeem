package com.islamicapp.siraathalmusthaqeem.utils

object PrayerMethodMapper {

    fun getMethodForCountry(countryCode: String?): Int {
        return when (countryCode?.uppercase()) {
            // Middle East
            "SA" -> 4   // Saudi Arabia
            "AE" -> 8   // UAE
            "QA" -> 10  // Qatar
            "KW" -> 9   // Kuwait
            "BH" -> 8   // Bahrain
            "OM" -> 8   // Oman

            // Egypt & North Africa
            "EG", "LY", "TN", "DZ", "MA" -> 5  // Egyptian

            // South Asia
            "PK", "IN", "BD" -> 1  // Karachi

            // Southeast Asia
            "MY", "ID", "BN" -> 3  // MWL
            "SG" -> 11  // Singapore

            // Turkey & Central Asia
            "TR", "AZ" -> 13  // Turkey
            "KZ", "UZ", "KG", "TJ", "TM" -> 14  // Russia

            // Iran
            "IR" -> 7  // Tehran

            // Europe
            "GB", "DE", "NL", "BE", "SE", "NO", "DK" -> 3  // MWL
            "FR" -> 12  // France

            // North America
            "US", "CA" -> 2  // ISNA

            // Russia & Eastern Europe
            "RU", "UA", "BY" -> 14  // Russia

            // Other Arab Countries
            "JO", "SY", "LB", "IQ", "YE", "PS" -> 4  // Makkah

            else -> 3  // MWL (default)
        }
    }

    fun getMethodName(methodId: Int): String {
        return when (methodId) {
            1 -> "Karachi"
            2 -> "ISNA"
            3 -> "MWL"
            4 -> "Makkah"
            5 -> "Egypt"
            7 -> "Tehran"
            8 -> "UAE"
            9 -> "Kuwait"
            10 -> "Qatar"
            11 -> "Singapore"
            12 -> "Morocco"
            13 -> "Turkey"
            14 -> "Russia"
            else -> "MWL"
        }
    }

    fun getAllMethods(): List<Pair<Int, String>> {
        return listOf(
            3 to "MWL",
            2 to "ISNA",
            5 to "Egypt",
            4 to "Makkah",
            1 to "Karachi",
            7 to "Tehran",
            11 to "Singapore",
            8 to "UAE",
            9 to "Kuwait",
            10 to "Qatar",
            13 to "Turkey",
            12 to "Morocco"
        )
    }
}