package com.islamicapp.siraathalmusthaqeem.utils

import android.widget.ProgressBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.ybq.android.spinkit.style.Circle
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun SpinKitLoader(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            ProgressBar(context).apply {
                indeterminateDrawable = Circle().apply {
                    setColor(context.getColor(R.color.teal_700))
                }
            }
        }
    )
}