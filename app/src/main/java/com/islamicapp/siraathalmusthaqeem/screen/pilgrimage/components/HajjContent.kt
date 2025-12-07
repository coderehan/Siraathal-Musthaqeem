package com.islamicapp.siraathalmusthaqeem.screen.pilgrimage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.islamicapp.domain.pilgrimage.model.Pilgrimage

@Composable
fun HajjContent(hajjData: Pilgrimage) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }

        // Display Types of Hajj
        hajjData.types_of_hajj?.let { typesOfHajj ->
            item {
                TypesOfHajjCard(typesOfHajj)
            }
        }

        // Display Preparation
        hajjData.preparation?.let { preparation ->
            item {
                PreparationCard(preparation)
            }
        }

        // Display each day
        hajjData.days?.forEach { day ->
            item {
                HajjDayCard(day)
            }
        }

        // Display Conclusion
        hajjData.conclusion?.dua?.let { dua ->
            item {
                ConclusionCard(dua)
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}