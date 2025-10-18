package com.islamicapp.siraathalmusthaqeem.screen.qibla.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islamicapp.siraathalmusthaqeem.R

@Composable
fun CompassUI(
    rotation: Float,
    needleRotation: Float,
    isFacingQibla: Boolean
) {
    val animatedCompassRotation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing),
        label = "compass-rotation"
    )

    val animatedNeedleRotation by animateFloatAsState(
        targetValue = needleRotation,
        animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing),
        label = "needle-rotation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated status text
        AnimatedContent(
            targetState = isFacingQibla,
            transitionSpec = {
                (fadeIn(tween(400)) + slideInVertically(initialOffsetY = { -it / 2 })) togetherWith
                        (fadeOut(tween(400)) + slideOutVertically(targetOffsetY = { it / 2 }))
            },
            label = "qibla-status"
        ) { facing ->
            val text = if (facing) "You're Facing Qibla" else "Turn Towards Qibla"
            val color = if (facing) Color(0xFF00897B) else Color(0xFF1F2937)
            androidx.compose.material3.Text(
                text = text,
                color = color,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 48.dp)
            )
        }

        // Compass visuals
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.ic_compass_direction),
                contentDescription = "Compass Base",
                modifier = Modifier
                    .size(500.dp)
                    .graphicsLayer { rotationZ = animatedCompassRotation }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_qibla_needle),
                contentDescription = "Qibla Needle",
                modifier = Modifier
                    .size(200.dp)
                    .graphicsLayer { rotationZ = animatedNeedleRotation }
            )
        }
    }
}
