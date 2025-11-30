package com.islamicapp.siraathalmusthaqeem.screen.quran.juz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.quran.components.JuzListItem

@Composable
fun JuzListScreen(navController: NavController) {

    val quranFont = FontFamily(Font(R.font.indo_pak_quran))

    // FIX: Create a list of Juz objects containing both names
    val JuzList = listOf(
        Juz("آلمَ", "Alif Laam Meem"),
        Juz("سَيَقُولُ", "Sayaqoolu"),
        Juz("تِلْكَ ٱلْرُّسُلَُ", "Tilkar Rusulu"),
        Juz("لَنْ تَنَالُوْ الْبِرَّ", "Lan Tanaalu"),
        Juz("وَٱلْمُحْصَنَاتُ", "Wal Muhsanaatu"),
        Juz("لَا يُحِبُّ ٱللهَُ", "La Yuhibbullahu"),
        Juz("وَإِذَا سَمِعُوا", "Waiza Samiu"),
        Juz("وَلَوْ أَنَّنَاَ", "Wa Lau Annana"),
        Juz("قَالَ ٱلْمَلَأُ", "Qaalal Malau"),
        Juz("وَٱعْلَمُواْ", "Wa'lamu"),
        Juz("يَعْتَذِرُونََ", "Ya'taziroon"),
        Juz("وَمَا مِنْ دَآبَّةٍ", "Wa Ma Min Daabbatin"),
        Juz("وَمَا أُبَرِّئَُ", "Wa Ma Ubarriu"),
        Juz("رُبَمَا", "Rubama"),
        Juz("سُبْحَانَ ٱلَّذِى", "Sub'haanallazi"),
        Juz("قَالَ أَلَمَْ", "Qaal Alam"),
        Juz("ٱقْتَرَبَ لِلْنَّاسِ", "Iqtaraba Linnaasi"),
        Juz("قَدْ أَفْلَحََ", "Qad Aflaha"),
        Juz("وَقَالَ ٱلَّذِينَ", "Waqaalallazeen"),
        Juz("أَمَّنْ خَلَقَ", "Amman Khalaqa"),
        Juz("أُتْلُ مَاأُوْحِیََ", "Utlu Maa Uhiya"),
        Juz("وَمَنْ يَّقْنُتْ", "Wamay Yaqnut"),
        Juz("وَمَآ ليَ", "Wamaaliya"),
        Juz("فَمَنْ أَظْلَمُ", "Faman Azlamu"),
        Juz("إِلَيْهِ يُرَدُّ", "Ilaihi Yuraddu"),
        Juz("حمَ", "Haa Meem"),
        Juz("قَالَ فَمَا خَطْبُكُم", "Qaala Fama Khatbukum"),
        Juz("قَدْ سَمِعَ ٱللهَُ", "Qad Sami'allahu"),
        Juz("تَبَارَكَ ٱلَّذِى", "Tabaarakallazi"),
        Juz("عَمَّ", "Amma")
    )

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(padding)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(JuzList.size) { index ->
                val juzNumber = index + 1
                JuzListItem(
                    juzNumber = juzNumber,
                    arabicName = JuzList[index].arabicName,
                    englishName = JuzList[index].englishName,
                    navController = navController,
                    quranFont = quranFont
                )
            }
        }
    }
}
