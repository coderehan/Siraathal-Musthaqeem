package com.islamicapp.siraathalmusthaqeem.screen.quran.surah

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.screen.quran.components.SurahListItem

// SurahListScreen.kt - Complete offline version
@Composable
fun SurahListScreen(
    navController: NavController
) {
    // Complete offline Surah list - all 114 Surahs
    val surahList = remember {
        listOf(
            Surah(1, "Al-Fatihah", "ٱلْفَاتِحَةُ", 7, "Meccan", "The Opening"),
            Surah(2, "Al-Baqarah", "ٱلْبَقَرَةُ", 286, "Medinan", "The Cow"),
            Surah(3, "Aali Imran", "ءَالِ عِمْرَانَ", 200, "Medinan", "The Family of Imran"),
            Surah(4, "An-Nisa", "ٱلنِّسَاءُ", 176, "Medinan", "The Women"),
            Surah(5, "Al-Ma'idah", "ٱلْمَآئِدَةُ", 120, "Medinan", "The Table Spread"),
            Surah(6, "Al-An'am", "ٱلْأَنْعَامُ", 165, "Meccan", "The Cattle"),
            Surah(7, "Al-A'raf", "ٱلْأَعْرَافُ", 206, "Meccan", "The Heights"),
            Surah(8, "Al-Anfal", "ٱلْأَنفَالُ", 75, "Medinan", "The Spoils of War"),
            Surah(9, "At-Tawbah", "ٱلتَّوْبَةُ", 129, "Medinan", "The Repentance"),
            Surah(10, "Yunus", "يُونُسُ", 109, "Meccan", "Jonah"),
            Surah(11, "Hud", "هُودٌ", 123, "Meccan", "Hud"),
            Surah(12, "Yusuf", "يُوسُفُ", 111, "Meccan", "Joseph"),
            Surah(13, "Ar-Ra'd", "ٱلرَّعْدُ", 43, "Medinan", "The Thunder"),
            Surah(14, "Ibrahim", "إِبْرَٰهِيمُ", 52, "Meccan", "Abraham"),
            Surah(15, "Al-Hijr", "ٱلْحِجْرُ", 99, "Meccan", "The Rocky Tract"),
            Surah(16, "An-Nahl", "ٱلنَّحْلُ", 128, "Meccan", "The Bee"),
            Surah(17, "Al-Isra", "ٱلْإِسْرَآءُ", 111, "Meccan", "The Night Journey"),
            Surah(18, "Al-Kahf", "ٱلْكَهْفُ", 110, "Meccan", "The Cave"),
            Surah(19, "Maryam", "مَرْيَمُ", 98, "Meccan", "Mary"),
            Surah(20, "Ta-Ha", "طَه", 135, "Meccan", "Ta-Ha"),
            Surah(21, "Al-Anbiya", "ٱلْأَنۢبِيَآءُ", 112, "Meccan", "The Prophets"),
            Surah(22, "Al-Hajj", "ٱلْحَجُّ", 78, "Medinan", "The Pilgrimage"),
            Surah(23, "Al-Mu'minun", "ٱلْمُؤْمِنُونَ", 118, "Meccan", "The Believers"),
            Surah(24, "An-Nur", "ٱلنُّورُ", 64, "Medinan", "The Light"),
            Surah(25, "Al-Furqan", "ٱلْفُرْقَانُ", 77, "Meccan", "The Criterion"),
            Surah(26, "Ash-Shu'ara", "ٱلشُّعَرَآءُ", 227, "Meccan", "The Poets"),
            Surah(27, "An-Naml", "ٱلنَّمْلُ", 93, "Meccan", "The Ant"),
            Surah(28, "Al-Qasas", "ٱلْقَصَصُ", 88, "Meccan", "The Stories"),
            Surah(29, "Al-Ankabut", "ٱلْعَنكَبُوتُ", 69, "Meccan", "The Spider"),
            Surah(30, "Ar-Rum", "ٱلرُّومُ", 60, "Meccan", "The Romans"),
            Surah(31, "Luqman", "لُقْمَٰنُ", 34, "Meccan", "Luqman"),
            Surah(32, "As-Sajdah", "ٱلسَّجْدَةُ", 30, "Meccan", "The Prostration"),
            Surah(33, "Al-Ahzab", "ٱلْأَحْزَابُ", 73, "Medinan", "The Combined Forces"),
            Surah(34, "Saba", "سَبَأٌ", 54, "Meccan", "Sheba"),
            Surah(35, "Fatir", "فَاطِرُ", 45, "Meccan", "The Originator"),
            Surah(36, "Ya-Sin", "يسٓ", 83, "Meccan", "Ya-Sin"),
            Surah(37, "As-Saffat", "ٱلصَّٰٓفَّٰتُ", 182, "Meccan", "Those Ranges in Ranks"),
            Surah(38, "Sad", "صٓ", 88, "Meccan", "Sad"),
            Surah(39, "Az-Zumar", "ٱلزُّمَرُ", 75, "Meccan", "The Groups"),
            Surah(40, "Ghafir", "غَافِرُ", 85, "Meccan", "The Forgiver"),
            Surah(41, "Fussilat", "فُصِّلَتْ", 54, "Meccan", "Explained in Detail"),
            Surah(42, "Ash-Shuraa", "ٱلشُّورَىٰ", 53, "Meccan", "The Consultation"),
            Surah(43, "Az-Zukhruf", "ٱلزُّخْرُفُ", 89, "Meccan", "The Gold Adornments"),
            Surah(44, "Ad-Dukhan", "ٱلدُّخَانُ", 59, "Meccan", "The Smoke"),
            Surah(45, "Al-Jathiyah", "ٱلْجَاثِيَةُ", 37, "Meccan", "The Kneeling"),
            Surah(46, "Al-Ahqaf", "ٱلْأَحْقَافُ", 35, "Meccan", "The Wind-Curved Sandhills"),
            Surah(47, "Muhammad", "مُحَمَّدٌ", 38, "Medinan", "Muhammad"),
            Surah(48, "Al-Fath", "ٱلْفَتْحُ", 29, "Medinan", "The Victory"),
            Surah(49, "Al-Hujurat", "ٱلْحُجُرَٰتُ", 18, "Medinan", "The Rooms"),
            Surah(50, "Qaf", "قٓ", 45, "Meccan", "Qaf"),
            Surah(51, "Adh-Dhariyat", "ٱلذَّٰرِيَٰتُ", 60, "Meccan", "The Winnowing Winds"),
            Surah(52, "At-Tur", "ٱلطُّورُ", 49, "Meccan", "The Mount"),
            Surah(53, "An-Najm", "ٱلنَّجْمُ", 62, "Meccan", "The Star"),
            Surah(54, "Al-Qamar", "ٱلْقَمَرُ", 55, "Meccan", "The Moon"),
            Surah(55, "Ar-Rahman", "ٱلرَّحْمَٰنُ", 78, "Medinan", "The Most Merciful"),
            Surah(56, "Al-Waqi'ah", "ٱلْوَاقِعَةُ", 96, "Meccan", "The Inevitable"),
            Surah(57, "Al-Hadid", "ٱلْحَدِيدُ", 29, "Medinan", "The Iron"),
            Surah(58, "Al-Mujadilah", "ٱلْمُجَٰدِلَةُ", 22, "Medinan", "The Pleading Woman"),
            Surah(59, "Al-Hashr", "ٱلْحَشْرُ", 24, "Medinan", "The Exile"),
            Surah(60, "Al-Mumtahanah", "ٱلْمُمْتَحَنَةُ", 13, "Medinan", "The Examined One"),
            Surah(61, "As-Saff", "ٱلصَّفُّ", 14, "Medinan", "The Ranks"),
            Surah(62, "Al-Jumu'ah", "ٱلْجُمُعَةُ", 11, "Medinan", "The Friday"),
            Surah(63, "Al-Munafiqun", "ٱلْمُنَٰفِقُونَ", 11, "Medinan", "The Hypocrites"),
            Surah(64, "At-Taghabun", "ٱلتَّغَابُنُ", 18, "Medinan", "The Mutual Disillusion"),
            Surah(65, "At-Talaq", "ٱلطَّلَاقُ", 12, "Medinan", "The Divorce"),
            Surah(66, "At-Tahrim", "ٱلتَّحْرِيمُ", 12, "Medinan", "The Prohibition"),
            Surah(67, "Al-Mulk", "ٱلْمُلْكُ", 30, "Meccan", "The Sovereignty"),
            Surah(68, "Al-Qalam", "ٱلْقَلَمُ", 52, "Meccan", "The Pen"),
            Surah(69, "Al-Haqqah", "ٱلْحَآقَّةُ", 52, "Meccan", "The Reality"),
            Surah(70, "Al-Ma'arij", "ٱلْمَعَارِجُ", 44, "Meccan", "The Ascending Stairways"),
            Surah(71, "Nuh", "نُوحٌ", 28, "Meccan", "Noah"),
            Surah(72, "Al-Jinn", "ٱلْجِنُّ", 28, "Meccan", "The Jinn"),
            Surah(73, "Al-Muzzammil", "ٱلْمُزَّمِّلُ", 20, "Meccan", "The Enshrouded One"),
            Surah(74, "Al-Muddaththir", "ٱلْمُدَّثِّرُ", 56, "Meccan", "The Cloaked One"),
            Surah(75, "Al-Qiyamah", "ٱلْقِيَٰمَةُ", 40, "Meccan", "The Resurrection"),
            Surah(76, "Al-Insan", "ٱلْإِنسَٰنُ", 31, "Medinan", "The Man"),
            Surah(77, "Al-Mursalat", "ٱلْمُرْسَلَٰتُ", 50, "Meccan", "The Emissaries"),
            Surah(78, "An-Naba", "ٱلنَّبَأُ", 40, "Meccan", "The Tidings"),
            Surah(79, "An-Nazi'at", "ٱلنَّٰزِعَٰتُ", 46, "Meccan", "Those Who Drag Forth"),
            Surah(80, "Abasa", "عَبَسَ", 42, "Meccan", "He Frowned"),
            Surah(81, "At-Takwir", "ٱلتَّكْوِيرُ", 29, "Meccan", "The Overthrowing"),
            Surah(82, "Al-Infitar", "ٱلْإِنفِطَارُ", 19, "Meccan", "The Cleaving"),
            Surah(83, "Al-Mutaffifin", "ٱلْمُطَفِّفُونَ", 36, "Meccan", "The Defrauding"),
            Surah(84, "Al-Inshiqaq", "ٱلْإِنسِقَاقُ", 25, "Meccan", "The Splitting Open"),
            Surah(85, "Al-Buruj", "ٱلْبُرُوجُ", 22, "Meccan", "The Mansions of the Stars"),
            Surah(86, "At-Tariq", "ٱلطَّارِقُ", 17, "Meccan", "The Night Comer"),
            Surah(87, "Al-A'la", "ٱلْأَعْلَىٰ", 19, "Meccan", "The Most High"),
            Surah(88, "Al-Ghashiyah", "ٱلْغَٰشِيَةُ", 26, "Meccan", "The Overwhelming"),
            Surah(89, "Al-Fajr", "ٱلْفَجْرُ", 30, "Meccan", "The Dawn"),
            Surah(90, "Al-Balad", "ٱلْبَلَدُ", 20, "Meccan", "The City"),
            Surah(91, "Ash-Shams", "ٱلشَّمْسُ", 15, "Meccan", "The Sun"),
            Surah(92, "Al-Layl", "ٱلَّيْلُ", 21, "Meccan", "The Night"),
            Surah(93, "Ad-Duhaa", "ٱلضُّحَىٰ", 11, "Meccan", "The Morning Hours"),
            Surah(94, "Ash-Sharh", "ٱلشَّرْحُ", 8, "Meccan", "The Relief"),
            Surah(95, "At-Tin", "ٱلتِّينُ", 8, "Meccan", "The Fig"),
            Surah(96, "Al-Alaq", "ٱلْعَلَقُ", 19, "Meccan", "The Clot"),
            Surah(97, "Al-Qadr", "ٱلْقَدْرُ", 5, "Meccan", "The Power"),
            Surah(98, "Al-Bayyinah", "ٱلْبَيِّنَةُ", 8, "Medinan", "The Clear Proof"),
            Surah(99, "Az-Zalzalah", "ٱلزَّلْزَلَةُ", 8, "Medinan", "The Earthquake"),
            Surah(100, "Al-Adiyat", "ٱلْعَٰدِيَٰتُ", 11, "Meccan", "The Courser"),
            Surah(101, "Al-Qari'ah", "ٱلْقَارِعَةُ", 11, "Meccan", "The Calamity"),
            Surah(102, "At-Takathur", "ٱلتَّكَاثُرُ", 8, "Meccan", "The Rivalry in World Increase"),
            Surah(103, "Al-Asr", "ٱلْعَصْرِ", 3, "Meccan", "The Declining Day"),
            Surah(104, "Al-Humazah", "ٱلْهُمَزَةِ", 9, "Meccan", "The Traducer"),
            Surah(105, "Al-Fil", "ٱلْفِيلِ", 5, "Meccan", "The Elephant"),
            Surah(106, "Quraysh", "قُرَيْشٍ", 4, "Meccan", "Quraysh"),
            Surah(107, "Al-Ma'un", "ٱلْمَاعُونِ", 7, "Meccan", "The Small Kindnesses"),
            Surah(108, "Al-Kawthar", "ٱلْكَوْثَرِ", 3, "Meccan", "The Abundance"),
            Surah(109, "Al-Kafirun", "ٱلْكَٰفِرُونَ", 6, "Meccan", "The Disbelievers"),
            Surah(110, "An-Nasr", "ٱلنَّصْرُ", 3, "Medinan", "The Divine Support"),
            Surah(111, "Al-Masad", "ٱلْمَسَدُ", 5, "Meccan", "The Palm Fiber"),
            Surah(112, "Al-Ikhlas", "ٱلْإِخْلَاصُ", 4, "Meccan", "The Sincerity"),
            Surah(113, "Al-Falaq", "ٱلْفَلَقِ", 5, "Meccan", "The Daybreak"),
            Surah(114, "An-Nas", "ٱلنَّاسِ", 6, "Meccan", "The Mankind")
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(8.dp)
    ) {
        LazyColumn {
            items(
                items = surahList,
                key = { it.surahNumber }
            ) { surah ->
                SurahListItem(
                    surah = surah,
                    navController = navController
                )
            }
        }
    }
}


