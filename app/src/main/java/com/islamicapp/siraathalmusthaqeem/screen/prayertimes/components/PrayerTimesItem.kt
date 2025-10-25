package com.islamicapp.siraathalmusthaqeem.screen.prayertimes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.islamicapp.domain.prayertimes.model.PrayerTimes
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PrayerTimesItem(
    prayerTimes: PrayerTimes,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // All prayers (24-hour format)
    val prayerMap = mapOf(
        "Fajr" to prayerTimes.fajr,
        "Sunrise" to prayerTimes.sunrise,
        "Dhuhr" to prayerTimes.dhuhr,
        "Asr" to prayerTimes.asr,
        "Sunset" to prayerTimes.sunset,
        "Maghrib" to prayerTimes.maghrib,
        "Isha" to prayerTimes.isha,
        "Tahajjud" to prayerTimes.tahajjud
    )

    // Rakat info for major prayers
    val rakatMap = mapOf(
        "Fajr" to "Sunnat-2, Farz-2",
        "Dhuhr" to "Sunnat-4, Farz-4, Sunnat-2, Nafl-2",
        "Asr" to "Sunnat-4, Farz-4",
        "Maghrib" to "Farz-3, Sunnat-2, Nafl-2",
        "Isha" to "Sunnat-4, Farz-4, Sunnat-2, Nafl-2, Witr-3, Sunnat-2"
    )

    // Current time from device
    val currentTime = remember { mutableStateOf(Date()) }

    // Update every second
    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = Date()
            kotlinx.coroutines.delay(1000)
        }
    }

    // Parse 24h prayer time to Date
    fun parsePrayerTime(time: String): Date? {
        return try {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val parsed = sdf.parse(time)
            val cal = Calendar.getInstance()
            val parsedCal = Calendar.getInstance()
            parsedCal.time = parsed!!
            cal.set(Calendar.HOUR_OF_DAY, parsedCal.get(Calendar.HOUR_OF_DAY))
            cal.set(Calendar.MINUTE, parsedCal.get(Calendar.MINUTE))
            cal.set(Calendar.SECOND, 0)
            cal.time
        } catch (e: Exception) {
            null
        }
    }

    // Determine current and next prayer
    val sortedPrayers = prayerMap.entries.mapNotNull { (name, time) ->
        parsePrayerTime(time)?.let { Pair(name, it) }
    }.sortedBy { it.second.time }

    val now = currentTime.value
    var currentPrayer by remember { mutableStateOf("Unknown") }
    var nextPrayerTime by remember { mutableStateOf<Date?>(null) }

    for (i in sortedPrayers.indices) {
        val prayer = sortedPrayers[i]
        val nextPrayer = sortedPrayers.getOrNull(i + 1)
        if (now.after(prayer.second) && (nextPrayer == null || now.before(nextPrayer.second))) {
            currentPrayer = prayer.first
            nextPrayerTime = nextPrayer?.second
            break
        }
    }

    // After Isha, next = Fajr (next day)
    if (nextPrayerTime == null && sortedPrayers.isNotEmpty()) {
        currentPrayer = sortedPrayers.last().first
        val cal = Calendar.getInstance()
        cal.time = parsePrayerTime(prayerTimes.fajr)!!
        cal.add(Calendar.DATE, 1)
        nextPrayerTime = cal.time
    }

    // Countdown to next prayer
    val remainingMillis = nextPrayerTime?.time?.minus(now.time)?.coerceAtLeast(0) ?: 0L
    val hours = (remainingMillis / (1000 * 60 * 60)).toInt()
    val minutes = ((remainingMillis / (1000 * 60)) % 60).toInt()
    val seconds = ((remainingMillis / 1000) % 60).toInt()
    val timeLeft = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    val date = prayerTimes.gregorianDate
    val hijriDate = "${prayerTimes.hijriDay} ${prayerTimes.hijriMonth} ${prayerTimes.hijriYear}"

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Now: $currentPrayer",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Circle timer
        Box(
            modifier = Modifier
                .size(150.dp)
                .border(3.dp, Color(0xFFCBD5E1), CircleShape)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Time", style = MaterialTheme.typography.bodySmall)
                Text(
                    timeLeft,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text("Left", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Show Rakat info if available
        rakatMap[currentPrayer]?.let { rakatInfo ->
            Text(
                text = rakatInfo,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF374151)
                ),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Date Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Center content properly using weight
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = date,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = hijriDate,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Prayer cards (highlight current)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        ) {
            prayerMap.forEach { (name, time) ->
                val isCurrent = name == currentPrayer
                PrayerTimeCard(
                    name = name,
                    time = formatTo12Hour(time),
                    isHighlighted = isCurrent
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            // Footer Note
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Note: The above timings denote the starting time of the Prayer (Azaan time).",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6B7280),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// 12-hour format
fun formatTo12Hour(time24: String): String {
    return try {
        val input = SimpleDateFormat("HH:mm", Locale.getDefault())
        val output = SimpleDateFormat("hh:mm a", Locale.getDefault())
        output.format(input.parse(time24)!!)
    } catch (e: Exception) {
        time24
    }
}

// Highlighted Card
@Composable
fun PrayerTimeCard(name: String, time: String, isHighlighted: Boolean) {
    val backgroundColor =
        if (isHighlighted) Color(0xFFD1FAE5) else Color.White
    val borderColor =
        if (isHighlighted) Color(0xFF10B981) else Color(0xFFE5E7EB)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(1.dp, borderColor, RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isHighlighted) 4.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Alarm,
                    contentDescription = null,
                    tint = if (isHighlighted) Color(0xFF059669) else Color(0xFF6B7280),
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Medium,
                        color = if (isHighlighted) Color(0xFF065F46) else Color(0xFF111827)
                    )
                )
            }
            Text(
                text = time,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = if (isHighlighted) Color(0xFF065F46) else Color(0xFF1F2937)
                )
            )
        }
    }
}
