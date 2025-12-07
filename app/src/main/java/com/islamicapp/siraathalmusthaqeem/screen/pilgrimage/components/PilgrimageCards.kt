package com.islamicapp.siraathalmusthaqeem.screen.pilgrimage.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islamicapp.domain.pilgrimage.model.Day
import com.islamicapp.domain.pilgrimage.model.Dua
import com.islamicapp.domain.pilgrimage.model.Preparation
import com.islamicapp.domain.pilgrimage.model.Section
import com.islamicapp.domain.pilgrimage.model.Step
import com.islamicapp.domain.pilgrimage.model.StoningSequence
import com.islamicapp.domain.pilgrimage.model.TypesOfHajj
import com.islamicapp.domain.pilgrimage.model.VisitingMadina
import com.islamicapp.siraathalmusthaqeem.R

// ========================================
// Common Components
// ========================================

@Composable
fun PilgrimageCardTitle(title: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.green))
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 12.dp)
        )
    }
}

@Composable
fun PilgrimageContentCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            content = content
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.green)
        ),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun InstructionText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = Color(0xFF374151),
            lineHeight = 22.sp,
            textAlign = TextAlign.Justify
        ),
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun BulletPointText(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = "• ",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF374151)
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF374151),
                lineHeight = 22.sp,
                textAlign = TextAlign.Justify
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DuaCard(dua: Dua) {
    val arabicFont = FontFamily(Font(R.font.indo_pak_quran))

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            dua.instruction?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF059669),
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            dua.arabic?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = arabicFont,
                        fontSize = 42.sp,
                        lineHeight = 36.sp,
                        color = Color(0xFF065F46),
                        textAlign = TextAlign.Right
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            dua.transliteration?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF047857),
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    ),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            dua.translation?.let {
                Divider(
                    color = Color(0xFF6EE7B7),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF047857)
                    )
                )
            }
        }
    }
}

// ========================================
// Umrah Components
// ========================================

@Composable
fun PilgrimageStepCard(step: Step) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Step Title
        PilgrimageCardTitle(
            title = "Step ${step.step_number}: ${step.title ?: ""}"
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Step Content
        PilgrimageContentCard {
            step.subtitle?.let {
                SectionTitle(it)
            }

            step.instructions?.forEach { instruction ->
                BulletPointText(instruction)
            }

            step.sections?.forEach { section ->
                SectionContent(section)
            }

            step.leaving_masjid?.let { leavingMasjid ->
                Spacer(modifier = Modifier.height(8.dp))
                InstructionText(leavingMasjid.instruction ?: "")
                leavingMasjid.dua?.let { DuaCard(it) }
            }

            step.completion?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.green)
                    )
                )
            }
        }
    }
}

@Composable
fun SectionContent(section: Section) {
    val arabicFont = FontFamily(Font(R.font.indo_pak_quran))

    Column(modifier = Modifier.fillMaxWidth()) {
        section.subtitle?.let {
            SectionTitle(it)
        }

        section.instruction?.let {
            InstructionText(it)
        }

        section.instructions?.forEach { instruction ->
            BulletPointText(instruction)
        }

        section.activities?.forEach { activity ->
            BulletPointText(activity)
        }

        section.notes?.forEach { note ->
            Text(
                text = "📝 $note",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6B7280),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        section.note?.let {
            Text(
                text = "📝 $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6B7280),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        section.prayer_note?.let {
            Text(
                text = "🕌 $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6B7280),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        section.niyyah?.let { DuaCard(it) }
        section.talbiyah?.let { DuaCard(it) }
        section.dua?.let { DuaCard(it) }
        section.takbir?.let { DuaCard(it) }

        section.special_dua?.let { specialDua ->
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    specialDua.location?.let {
                        Text(
                            text = "📍 $it",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF059669),
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    specialDua.instruction?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF059669),
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    specialDua.arabic?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = arabicFont,
                                fontSize = 42.sp,
                                lineHeight = 36.sp,
                                color = Color(0xFF065F46),
                                textAlign = TextAlign.Right
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }

                    specialDua.transliteration?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF047857),
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            ),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    specialDua.translation?.let {
                        Divider(
                            color = Color(0xFF6EE7B7),
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF047857)
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VisitingMadinaCard(visitingMadina: VisitingMadina) {
    Column(modifier = Modifier.fillMaxWidth()) {
        PilgrimageCardTitle(title = visitingMadina.title ?: "Visiting Madina")

        Spacer(modifier = Modifier.height(6.dp))

        PilgrimageContentCard {
            Text(
                text = if (visitingMadina.is_obligatory == true) "Obligatory" else "Not Obligatory",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (visitingMadina.is_obligatory == true) Color.Red else colorResource(
                        id = R.color.green
                    )
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            visitingMadina.description?.let {
                InstructionText(it)
            }

            visitingMadina.hadith?.forEach { hadith ->
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = hadith.text ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF1E40AF),
                                lineHeight = 22.sp,
                                textAlign = TextAlign.Justify
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "— ${hadith.narrator ?: ""}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF3B82F6),
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        )
                    }
                }
            }
        }
    }
}

// ========================================
// Hajj Components
// ========================================

@Composable
fun TypesOfHajjCard(typesOfHajj: TypesOfHajj) {
    Column(modifier = Modifier.fillMaxWidth()) {
        PilgrimageCardTitle(title = "Types of Hajj")

        Spacer(modifier = Modifier.height(6.dp))

        PilgrimageContentCard {
            typesOfHajj.description?.let {
                InstructionText(it)
                Spacer(modifier = Modifier.height(12.dp))
            }

            typesOfHajj.types?.forEach { type ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF0F9FF)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = type.name ?: "",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.green)
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = type.description ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF374151),
                                lineHeight = 20.sp
                            )
                        )
                        if (type.requires_sacrifice == true) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "⚠️ Requires Sacrifice (Udhiya)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFFDC2626),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }

            typesOfHajj.note?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "📝 $it",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF6B7280),
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                )
            }
        }
    }
}

@Composable
fun PreparationCard(preparation: Preparation) {
    Column(modifier = Modifier.fillMaxWidth()) {
        PilgrimageCardTitle(title = "Preparation")

        Spacer(modifier = Modifier.height(6.dp))

        PilgrimageContentCard {
            SectionTitle("Before Departure")
            preparation.before_departure?.forEach { item ->
                BulletPointText(item)
            }

            preparation.upon_arrival_makkah?.let {
                Spacer(modifier = Modifier.height(12.dp))
                SectionTitle("Upon Arrival in Makkah")
                InstructionText(it)
            }
        }
    }
}

@Composable
fun HajjDayCard(day: Day) {
    Column(modifier = Modifier.fillMaxWidth()) {
        PilgrimageCardTitle(
            title = "${day.day ?: ""} - ${day.name ?: ""}"
        )

        Spacer(modifier = Modifier.height(6.dp))

        PilgrimageContentCard {
            day.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.green)
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            day.sections?.forEach { section ->
                HajjSectionContent(section)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun HajjSectionContent(section: Section) {
    val arabicFont = FontFamily(Font(R.font.indo_pak_quran))

    Column(modifier = Modifier.fillMaxWidth()) {
        section.subtitle?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF059669)
                ),
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }

        section.location?.let {
            Text(
                text = "📍 $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6B7280)
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        section.timing?.let {
            Text(
                text = "⏰ $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6B7280)
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        section.instruction?.let {
            InstructionText(it)
        }

        section.instructions?.forEach { instruction ->
            BulletPointText(instruction)
        }

        section.activities?.forEach { activity ->
            BulletPointText(activity)
        }

        section.total_stones?.let {
            Text(
                text = "🪨 Stones Required: $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF059669),
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        section.men?.let {
            Text(
                text = "👨 Men: $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF374151)
                ),
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }

        section.women?.let {
            Text(
                text = "👩 Women: $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF374151)
                ),
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }

        section.note?.let {
            Text(
                text = "📝 $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6B7280),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        section.prayer_note?.let {
            Text(
                text = "🕌 $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6B7280),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        section.pebble_note?.let {
            Text(
                text = "🪨 $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6B7280),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        section.women_note?.let {
            Text(
                text = "👩 $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6B7280),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        section.leaving_option?.let {
            Text(
                text = "ℹ️ $it",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF2563EB),
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        section.niyyah?.let { DuaCard(it) }
        section.talbiyah?.let { DuaCard(it) }
        section.best_supplication?.let { DuaCard(it) }
        section.recitation?.let { DuaCard(it) }

        section.sequence?.forEach { stoningSeq ->
            StoningSequenceCard(stoningSeq)
        }

        section.leaving?.let { leaving ->
            Spacer(modifier = Modifier.height(8.dp))
            InstructionText(leaving.instruction ?: "")
            leaving.dua?.let { DuaCard(it) }
        }
    }
}

@Composable
fun StoningSequenceCard(sequence: StoningSequence) {
    val arabicFont = FontFamily(Font(R.font.indo_pak_quran))

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            sequence.jamarah?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF059669)
                    )
                )
            }

            sequence.position?.let {
                Text(
                    text = "Position: $it",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF047857)
                    ),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            sequence.stones?.let {
                Text(
                    text = "🪨 Throw $it stones",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF047857),
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            sequence.recitation?.let { recitation ->
                Spacer(modifier = Modifier.height(8.dp))
                recitation.arabic?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontFamily = arabicFont,
                            fontSize = 42.sp,
                            lineHeight = 36.sp,
                            color = Color(0xFF065F46),
                            textAlign = TextAlign.Right
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                recitation.translation?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF047857),
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )
                }
            }

            sequence.after_stoning?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "After stoning: $it",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF047857)
                    )
                )
            }
        }
    }
}

@Composable
fun ConclusionCard(dua: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        PilgrimageCardTitle(title = "Conclusion")

        Spacer(modifier = Modifier.height(6.dp))

        PilgrimageContentCard {
            Text(
                text = dua,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.green),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}