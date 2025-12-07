package com.islamicapp.siraathalmusthaqeem.screen.home.components.dailyslider

import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun RandomQuranVerseView(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val view = remember {
        LayoutInflater.from(context)
            .inflate(R.layout.view_quran_verse, null, false)
    }

    AndroidView(
        modifier = modifier,
        factory = { view }
    )
}



