package com.islamicapp.siraathalmusthaqeem.screen.azaan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AzaanScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = false
            val mediaItem =
                MediaItem.fromUri("android.resource://${context.packageName}/${R.raw.azaan_makkah}")
            setMediaItem(mediaItem)
            prepare()
        }
    }

    var isPlaying by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            player.pause()
            player.release()
            isPlaying = false
        }
    }

    Scaffold(
        topBar = {
            CommonActionBar(
                title = "Azaan",
                navController = navController
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(padding)
        ) {
            // 🕋 Semi-transparent background image
            Image(
                painter = painterResource(id = R.drawable.makkah_background),
                contentDescription = "Makkah Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.25f)
            )

            // ▶Play / Pause at Bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        isPlaying = if (isPlaying) {
                            player.pause()
                            false
                        } else {
                            player.play()
                            true
                        }
                    },
                    modifier = Modifier
                        .size(80.dp)
                        .background(colorResource(id = R.color.green), CircleShape)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play or Pause",
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}


