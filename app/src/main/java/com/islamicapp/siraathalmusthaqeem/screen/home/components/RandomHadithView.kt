package com.islamicapp.siraathalmusthaqeem.screen.home.components

import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun RandomHadithView(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            LayoutInflater.from(context).inflate(R.layout.view_random_hadith, null, false)
        }
    )
}
