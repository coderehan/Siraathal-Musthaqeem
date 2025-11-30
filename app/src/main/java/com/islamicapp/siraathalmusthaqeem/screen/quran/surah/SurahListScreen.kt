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
            Surah(1, "Al-Fatihah", "الفاتحة", 7, "Meccan", "The Opening"),
            Surah(2, "Al-Baqarah", "البقرة", 286, "Medinan", "The Cow"),
            Surah(3, "Aali Imran", "آل عمران", 200, "Medinan", "The Family of Imran"),
            Surah(4, "An-Nisa", "النساء", 176, "Medinan", "The Women"),
            Surah(5, "Al-Ma'idah", "المائدة", 120, "Medinan", "The Table Spread"),
            Surah(6, "Al-An'am", "الأنعام", 165, "Meccan", "The Cattle"),
            Surah(7, "Al-A'raf", "الأعراف", 206, "Meccan", "The Heights"),
            Surah(8, "Al-Anfal", "الأنفال", 75, "Medinan", "The Spoils of War"),
            Surah(9, "At-Tawbah", "التوبة", 129, "Medinan", "The Repentance"),
            Surah(10, "Yunus", "يونس", 109, "Meccan", "Jonah"),
            Surah(11, "Hud", "هود", 123, "Meccan", "Hud"),
            Surah(12, "Yusuf", "يوسف", 111, "Meccan", "Joseph"),
            Surah(13, "Ar-Ra'd", "الرعد", 43, "Medinan", "The Thunder"),
            Surah(14, "Ibrahim", "ابراهيم", 52, "Meccan", "Abraham"),
            Surah(15, "Al-Hijr", "الحجر", 99, "Meccan", "The Rocky Tract"),
            Surah(16, "An-Nahl", "النحل", 128, "Meccan", "The Bee"),
            Surah(17, "Al-Isra", "الإسراء", 111, "Meccan", "The Night Journey"),
            Surah(18, "Al-Kahf", "الكهف", 110, "Meccan", "The Cave"),
            Surah(19, "Maryam", "مريم", 98, "Meccan", "Mary"),
            Surah(20, "Ta-Ha", "طه", 135, "Meccan", "Ta-Ha"),
            Surah(21, "Al-Anbiya", "الأنبياء", 112, "Meccan", "The Prophets"),
            Surah(22, "Al-Hajj", "الحج", 78, "Medinan", "The Pilgrimage"),
            Surah(23, "Al-Mu'minun", "المؤمنون", 118, "Meccan", "The Believers"),
            Surah(24, "An-Nur", "النور", 64, "Medinan", "The Light"),
            Surah(25, "Al-Furqan", "الفرقان", 77, "Meccan", "The Criterion"),
            Surah(26, "Ash-Shu'ara", "الشعراء", 227, "Meccan", "The Poets"),
            Surah(27, "An-Naml", "النمل", 93, "Meccan", "The Ant"),
            Surah(28, "Al-Qasas", "القصص", 88, "Meccan", "The Stories"),
            Surah(29, "Al-Ankabut", "العنكبوت", 69, "Meccan", "The Spider"),
            Surah(30, "Ar-Rum", "الروم", 60, "Meccan", "The Romans"),
            Surah(31, "Luqman", "لقمان", 34, "Meccan", "Luqman"),
            Surah(32, "As-Sajdah", "السجدة", 30, "Meccan", "The Prostration"),
            Surah(33, "Al-Ahzab", "الأحزاب", 73, "Medinan", "The Combined Forces"),
            Surah(34, "Saba", "سبإ", 54, "Meccan", "Sheba"),
            Surah(35, "Fatir", "فاطر", 45, "Meccan", "The Originator"),
            Surah(36, "Ya-Sin", "يس", 83, "Meccan", "Ya-Sin"),
            Surah(37, "As-Saffat", "الصافات", 182, "Meccan", "Those Ranges in Ranks"),
            Surah(38, "Sad", "ص", 88, "Meccan", "Sad"),
            Surah(39, "Az-Zumar", "الزمر", 75, "Meccan", "The Groups"),
            Surah(40, "Ghafir", "غافر", 85, "Meccan", "The Forgiver"),
            Surah(41, "Fussilat", "فصلت", 54, "Meccan", "Explained in Detail"),
            Surah(42, "Ash-Shuraa", "الشورى", 53, "Meccan", "The Consultation"),
            Surah(43, "Az-Zukhruf", "الزخرف", 89, "Meccan", "The Gold Adornments"),
            Surah(44, "Ad-Dukhan", "الدخان", 59, "Meccan", "The Smoke"),
            Surah(45, "Al-Jathiyah", "الجاثية", 37, "Meccan", "The Kneeling"),
            Surah(46, "Al-Ahqaf", "الأحقاف", 35, "Meccan", "The Wind-Curved Sandhills"),
            Surah(47, "Muhammad", "محمد", 38, "Medinan", "Muhammad"),
            Surah(48, "Al-Fath", "الفتح", 29, "Medinan", "The Victory"),
            Surah(49, "Al-Hujurat", "الحجرات", 18, "Medinan", "The Rooms"),
            Surah(50, "Qaf", "ق", 45, "Meccan", "Qaf"),
            Surah(51, "Adh-Dhariyat", "الذاريات", 60, "Meccan", "The Winnowing Winds"),
            Surah(52, "At-Tur", "الطور", 49, "Meccan", "The Mount"),
            Surah(53, "An-Najm", "النجم", 62, "Meccan", "The Star"),
            Surah(54, "Al-Qamar", "القمر", 55, "Meccan", "The Moon"),
            Surah(55, "Ar-Rahman", "الرحمن", 78, "Medinan", "The Most Merciful"),
            Surah(56, "Al-Waqi'ah", "الواقعة", 96, "Meccan", "The Inevitable"),
            Surah(57, "Al-Hadid", "الحديد", 29, "Medinan", "The Iron"),
            Surah(58, "Al-Mujadilah", "المجادلة", 22, "Medinan", "The Pleading Woman"),
            Surah(59, "Al-Hashr", "الحشر", 24, "Medinan", "The Exile"),
            Surah(60, "Al-Mumtahanah", "الممتحنة", 13, "Medinan", "The Examined One"),
            Surah(61, "As-Saff", "الصف", 14, "Medinan", "The Ranks"),
            Surah(62, "Al-Jumu'ah", "الجمعة", 11, "Medinan", "The Friday"),
            Surah(63, "Al-Munafiqun", "المنافقون", 11, "Medinan", "The Hypocrites"),
            Surah(64, "At-Taghabun", "التغابن", 18, "Medinan", "The Mutual Disillusion"),
            Surah(65, "At-Talaq", "الطلاق", 12, "Medinan", "The Divorce"),
            Surah(66, "At-Tahrim", "التحريم", 12, "Medinan", "The Prohibition"),
            Surah(67, "Al-Mulk", "الملك", 30, "Meccan", "The Sovereignty"),
            Surah(68, "Al-Qalam", "القلم", 52, "Meccan", "The Pen"),
            Surah(69, "Al-Haqqah", "الحاقة", 52, "Meccan", "The Reality"),
            Surah(70, "Al-Ma'arij", "المعارج", 44, "Meccan", "The Ascending Stairways"),
            Surah(71, "Nuh", "نوح", 28, "Meccan", "Noah"),
            Surah(72, "Al-Jinn", "الجن", 28, "Meccan", "The Jinn"),
            Surah(73, "Al-Muzzammil", "المزمل", 20, "Meccan", "The Enshrouded One"),
            Surah(74, "Al-Muddaththir", "المدثر", 56, "Meccan", "The Cloaked One"),
            Surah(75, "Al-Qiyamah", "القيامة", 40, "Meccan", "The Resurrection"),
            Surah(76, "Al-Insan", "الانسان", 31, "Medinan", "The Man"),
            Surah(77, "Al-Mursalat", "المرسلات", 50, "Meccan", "The Emissaries"),
            Surah(78, "An-Naba", "النبإ", 40, "Meccan", "The Tidings"),
            Surah(79, "An-Nazi'at", "النازعات", 46, "Meccan", "Those Who Drag Forth"),
            Surah(80, "Abasa", "عبس", 42, "Meccan", "He Frowned"),
            Surah(81, "At-Takwir", "التكوير", 29, "Meccan", "The Overthrowing"),
            Surah(82, "Al-Infitar", "الإنفطار", 19, "Meccan", "The Cleaving"),
            Surah(83, "Al-Mutaffifin", "المطففين", 36, "Meccan", "The Defrauding"),
            Surah(84, "Al-Inshiqaq", "الإنشقاق", 25, "Meccan", "The Splitting Open"),
            Surah(85, "Al-Buruj", "البروج", 22, "Meccan", "The Mansions of the Stars"),
            Surah(86, "At-Tariq", "الطارق", 17, "Meccan", "The Night Comer"),
            Surah(87, "Al-A'la", "الأعلى", 19, "Meccan", "The Most High"),
            Surah(88, "Al-Ghashiyah", "الغاشية", 26, "Meccan", "The Overwhelming"),
            Surah(89, "Al-Fajr", "الفجر", 30, "Meccan", "The Dawn"),
            Surah(90, "Al-Balad", "البلد", 20, "Meccan", "The City"),
            Surah(91, "Ash-Shams", "الشمس", 15, "Meccan", "The Sun"),
            Surah(92, "Al-Layl", "الليل", 21, "Meccan", "The Night"),
            Surah(93, "Ad-Duhaa", "الضحى", 11, "Meccan", "The Morning Hours"),
            Surah(94, "Ash-Sharh", "الشرح", 8, "Meccan", "The Relief"),
            Surah(95, "At-Tin", "التين", 8, "Meccan", "The Fig"),
            Surah(96, "Al-Alaq", "العلق", 19, "Meccan", "The Clot"),
            Surah(97, "Al-Qadr", "القدر", 5, "Meccan", "The Power"),
            Surah(98, "Al-Bayyinah", "البينة", 8, "Medinan", "The Clear Proof"),
            Surah(99, "Az-Zalzalah", "الزلزلة", 8, "Medinan", "The Earthquake"),
            Surah(100, "Al-Adiyat", "العاديات", 11, "Meccan", "The Courser"),
            Surah(101, "Al-Qari'ah", "القارعة", 11, "Meccan", "The Calamity"),
            Surah(102, "At-Takathur", "التكاثر", 8, "Meccan", "The Rivalry in World Increase"),
            Surah(103, "Al-Asr", "العصر", 3, "Meccan", "The Declining Day"),
            Surah(104, "Al-Humazah", "الهمزة", 9, "Meccan", "The Traducer"),
            Surah(105, "Al-Fil", "الفيل", 5, "Meccan", "The Elephant"),
            Surah(106, "Quraysh", "قريش", 4, "Meccan", "Quraysh"),
            Surah(107, "Al-Ma'un", "الماعون", 7, "Meccan", "The Small Kindnesses"),
            Surah(108, "Al-Kawthar", "الكوثر", 3, "Meccan", "The Abundance"),
            Surah(109, "Al-Kafirun", "الكافرون", 6, "Meccan", "The Disbelievers"),
            Surah(110, "An-Nasr", "النصر", 3, "Medinan", "The Divine Support"),
            Surah(111, "Al-Masad", "المسد", 5, "Meccan", "The Palm Fiber"),
            Surah(112, "Al-Ikhlas", "الإخلاص", 4, "Meccan", "The Sincerity"),
            Surah(113, "Al-Falaq", "الفلق", 5, "Meccan", "The Daybreak"),
            Surah(114, "An-Nas", "الناس", 6, "Meccan", "The Mankind")
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


