package com.islamicapp.domain.pilgrimage.model

data class Pilgrimage(
    val title: String? = null,
    val description: String? = null,
    val types_of_hajj: TypesOfHajj? = null,
    val preparation: Preparation? = null,
    val steps: List<Step>? = null,
    val days: List<Day>? = null,
    val additional_info: AdditionalInfo? = null,
    val metro_train_info: MetroTrainInfo? = null,
    val conclusion: Conclusion? = null
)

data class TypesOfHajj(
    val description: String? = null,
    val types: List<HajjType>? = null,
    val note: String? = null
)

data class HajjType(
    val name: String? = null,
    val description: String? = null,
    val requires_sacrifice: Boolean? = null
)

data class Preparation(
    val before_departure: List<String>? = null,
    val upon_arrival_makkah: String? = null
)

data class Step(
    val step_number: Int? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val sections: List<Section>? = null,
    val instructions: List<String>? = null,
    val leaving_masjid: LeavingMasjid? = null,
    val completion: String? = null
)

data class Day(
    val day: String? = null,
    val name: String? = null,
    val title: String? = null,
    val sections: List<Section>? = null
)

data class Section(
    val subtitle: String? = null,
    val instruction: String? = null,
    val instructions: List<String>? = null,
    val activities: List<String>? = null,
    val location: String? = null,
    val timing: String? = null,
    val total_stones: String? = null,
    val men: String? = null,
    val women: String? = null,
    val note: String? = null,
    val notes: List<String>? = null,
    val prayer_note: String? = null,
    val pebble_note: String? = null,
    val women_note: String? = null,
    val leaving_option: String? = null,
    val niyyah: Dua? = null,
    val talbiyah: Dua? = null,
    val dua: Dua? = null,
    val takbir: Dua? = null,
    val special_dua: SpecialDua? = null,
    val best_supplication: Dua? = null,
    val recitation: Dua? = null,
    val sequence: List<StoningSequence>? = null,
    val leaving: LeavingMasjid? = null
)

data class Dua(
    val title: String? = null,
    val description: String? = null,
    val instruction: String? = null,
    val location: String? = null,
    val arabic: String? = null,
    val transliteration: String? = null,
    val translation: String? = null
)

data class SpecialDua(
    val location: String? = null,
    val instruction: String? = null,
    val arabic: String? = null,
    val transliteration: String? = null,
    val translation: String? = null
)

data class LeavingMasjid(
    val instruction: String? = null,
    val dua: Dua? = null
)

data class StoningSequence(
    val jamarah: String? = null,
    val position: String? = null,
    val stones: Int? = null,
    val recitation: Dua? = null,
    val after_stoning: String? = null
)

data class AdditionalInfo(
    val visiting_madina: VisitingMadina? = null
)

data class VisitingMadina(
    val title: String? = null,
    val is_obligatory: Boolean? = null,
    val description: String? = null,
    val hadith: List<Hadith>? = null
)

data class Hadith(
    val text: String? = null,
    val narrator: String? = null
)

data class MetroTrainInfo(
    val title: String? = null,
    val description: String? = null,
    val stations: List<Station>? = null
)

data class Station(
    val name: String? = null,
    val color: String? = null,
    val locations: Map<String, String>? = null
)

data class Conclusion(
    val dua: String? = null
)