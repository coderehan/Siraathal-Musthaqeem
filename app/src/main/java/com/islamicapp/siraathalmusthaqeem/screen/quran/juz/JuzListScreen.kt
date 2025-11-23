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

// Define a data class to hold Juz information
data class JuzInfo(
    val arabicName: String,
    val englishName: String
)

@Composable
fun JuzListScreen(navController: NavController) {

    val quranFont = FontFamily(Font(R.font.indo_pak_quran))

    // FIX: Create a list of JuzInfo objects containing both names
    val juzInfoList = listOf(
        JuzInfo("آلمَ", "Alif Laam Meem"),
        JuzInfo("سَيَقُولُ", "Sayaqoolu"),
        JuzInfo("تِلْكَ ٱلْرُّسُلَُ", "Tilkar Rusulu"),
        JuzInfo("لَنْ تَنَالُوْ الْبِرَّ", "Lan Tanaalu"),
        JuzInfo("وَٱلْمُحْصَنَاتُ", "Wal Muhsanaatu"),
        JuzInfo("لَا يُحِبُّ ٱللهَُ", "La Yuhibbullahu"),
        JuzInfo("وَإِذَا سَمِعُوا", "Waiza Samiu"),
        JuzInfo("وَلَوْ أَنَّنَاَ", "Wa Lau Annana"),
        JuzInfo("قَالَ ٱلْمَلَأُ", "Qaalal Malau"),
        JuzInfo("وَٱعْلَمُواْ", "Wa'lamu"),
        JuzInfo("يَعْتَذِرُونََ", "Ya'taziroon"),
        JuzInfo("وَمَا مِنْ دَآبَّةٍ", "Wa Ma Min Daabbatin"),
        JuzInfo("وَمَا أُبَرِّئَُ", "Wa Ma Ubarriu"),
        JuzInfo("رُبَمَا", "Rubama"),
        JuzInfo("سُبْحَانَ ٱلَّذِى", "Sub'haanallazi"),
        JuzInfo("قَالَ أَلَمَْ", "Qaal Alam"),
        JuzInfo("ٱقْتَرَبَ لِلْنَّاسِ", "Iqtaraba Linnaasi"),
        JuzInfo("قَدْ أَفْلَحََ", "Qad Aflaha"),
        JuzInfo("وَقَالَ ٱلَّذِينَ", "Waqaalallazeen"),
        JuzInfo("أَمَّنْ خَلَقَ", "Amman Khalaqa"),
        JuzInfo("أُتْلُ مَاأُوْحِیََ", "Utlu Maa Uhiya"),
        JuzInfo("وَمَنْ يَّقْنُتْ", "Wamay Yaqnut"),
        JuzInfo("وَمَآ ليَ", "Wamaaliya"),
        JuzInfo("فَمَنْ أَظْلَمُ", "Faman Azlamu"),
        JuzInfo("إِلَيْهِ يُرَدُّ", "Ilaihi Yuraddu"),
        JuzInfo("حمَ", "Haa Meem"),
        JuzInfo("قَالَ فَمَا خَطْبُكُم", "Qaala Fama Khatbukum"),
        JuzInfo("قَدْ سَمِعَ ٱللهَُ", "Qad Sami'allahu"),
        JuzInfo("تَبَارَكَ ٱلَّذِى", "Tabaarakallazi"),
        JuzInfo("عَمَّ", "Amma")
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
            items(juzInfoList.size) { index ->
                val juzNumber = index + 1
                JuzListItem(
                    juzNumber = juzNumber,
                    arabicName = juzInfoList[index].arabicName,
                    englishName = juzInfoList[index].englishName,
                    navController = navController,
                    quranFont = quranFont
                )
            }
        }
    }
}
