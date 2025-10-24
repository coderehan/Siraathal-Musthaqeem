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
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar

@Composable
fun JuzListScreen(navController: NavController) {

    val quranFont = FontFamily(Font(R.font.indo_pak_quran))

    val juzList = listOf(
        "آلمَ",
        "سَيَقُولُ",
        "تِلْكَ ٱلْرُّسُلَُ",
        "لَنْ تَنَالُوْ الْبِرَّ",
        "وَٱلْمُحْصَنَاتُ",
        "لَا يُحِبُّ ٱللهَُ",
        "وَإِذَا سَمِعُوا",
        "وَلَوْ أَنَّنَاَ",
        "قَالَ ٱلْمَلَأُ",
        "وَٱعْلَمُواْ",
        "يَعْتَذِرُونََ",
        "وَمَا مِنْ دَآبَّةٍ",
        "وَمَا أُبَرِّئَُ",
        "رُبَمَا",
        "سُبْحَانَ ٱلَّذِى",
        "قَالَ أَلَمَْ",
        "ٱقْتَرَبَ لِلْنَّاسِ",
        "قَدْ أَفْلَحََ",
        "وَقَالَ ٱلَّذِينَ",
        "أَمَّنْ خَلَقَ",
        "أُتْلُ مَاأُوْحِیََ",
        "وَمَنْ يَّقْنُتْ",
        "وَمَآ ليَ",
        "فَمَنْ أَظْلَمُ",
        "إِلَيْهِ يُرَدُّ",
        "حمَ",
        "قَالَ فَمَا خَطْبُكُم",
        "قَدْ سَمِعَ ٱللهَُ",
        "تَبَارَكَ ٱلَّذِى",
        "عَمَّ",
    )

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(padding)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(juzList.size) { index ->
                val juzNumber = index + 1
                JuzListItem(
                    juzNumber = juzNumber,
                    arabicName = juzList[index],
                    navController = navController,
                    quranFont = quranFont
                )
            }
        }
    }
}
