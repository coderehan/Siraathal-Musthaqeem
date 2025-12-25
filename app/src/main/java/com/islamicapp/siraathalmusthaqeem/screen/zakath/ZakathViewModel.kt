package com.islamicapp.siraathalmusthaqeem.screen.zakath

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ZakatState(
    // Market Rates (per gram)
    val goldRatePerGram: String = "",
    val silverRatePerGram: String = "",

    // Assets
    val goldWeightGrams: String = "",
    val silverWeightGrams: String = "",
    val cashInHandBank: String = "",
    val otherInvestments: String = "",

    // Liabilities
    val liabilities: String = "",

    // Results
    val totalNetWorth: Double = 0.0,
    val nisabThreshold: Double = 0.0,
    val goldNisabValue: Double = 0.0,
    val silverNisabValue: Double = 0.0,
    val zakatPayable: Double = 0.0,
    val isEligible: Boolean = false,
    val calculated: Boolean = false,
    val validationError: String? = null
)

class ZakathViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ZakatState())
    val uiState: StateFlow<ZakatState> = _uiState.asStateFlow()

    // Nisab constants (in grams)
    companion object {
        const val GOLD_NISAB_GRAMS = 87.48  // Approximately 7.5 tola
        const val SILVER_NISAB_GRAMS = 612.36  // Approximately 52.5 tola
        const val ZAKAT_PERCENTAGE = 0.025  // 2.5%
    }

    // Update functions for UI inputs
    fun updateGoldRate(value: String) = _uiState.update { it.copy(goldRatePerGram = value, calculated = false, validationError = null) }
    fun updateSilverRate(value: String) = _uiState.update { it.copy(silverRatePerGram = value, calculated = false, validationError = null) }
    fun updateGoldWeight(value: String) = _uiState.update { it.copy(goldWeightGrams = value, calculated = false, validationError = null) }
    fun updateSilverWeight(value: String) = _uiState.update { it.copy(silverWeightGrams = value, calculated = false, validationError = null) }
    fun updateCash(value: String) = _uiState.update { it.copy(cashInHandBank = value, calculated = false, validationError = null) }
    fun updateInvestments(value: String) = _uiState.update { it.copy(otherInvestments = value, calculated = false, validationError = null) }
    fun updateLiabilities(value: String) = _uiState.update { it.copy(liabilities = value, calculated = false, validationError = null) }

    fun calculateZakat() {
        val state = _uiState.value

        // Parse inputs (default to 0.0 if empty)
        val gRate = state.goldRatePerGram.toDoubleOrNull() ?: 0.0
        val sRate = state.silverRatePerGram.toDoubleOrNull() ?: 0.0
        val gWeight = state.goldWeightGrams.toDoubleOrNull() ?: 0.0
        val sWeight = state.silverWeightGrams.toDoubleOrNull() ?: 0.0
        val cash = state.cashInHandBank.toDoubleOrNull() ?: 0.0
        val investments = state.otherInvestments.toDoubleOrNull() ?: 0.0
        val debts = state.liabilities.toDoubleOrNull() ?: 0.0

        // Validation: At least one rate must be provided
        if (gRate <= 0.0 && sRate <= 0.0) {
            _uiState.update {
                it.copy(
                    validationError = "Please enter at least Gold or Silver rate",
                    calculated = false
                )
            }
            return
        }

        // 1. Calculate Asset Values
        val goldValue = gWeight * gRate
        val silverValue = sWeight * sRate

        // 2. Calculate Total Net Worth
        val totalAssets = goldValue + silverValue + cash + investments
        val netWorth = (totalAssets - debts).coerceAtLeast(0.0)

        // 3. Calculate Both Nisab Thresholds
        val goldNisabValue = if (gRate > 0.0) GOLD_NISAB_GRAMS * gRate else Double.MAX_VALUE
        val silverNisabValue = if (sRate > 0.0) SILVER_NISAB_GRAMS * sRate else Double.MAX_VALUE

        // 4. Use the LOWER nisab threshold (more beneficial to poor)
        // If both rates are missing, use available one
        val nisabThreshold = when {
            gRate > 0.0 && sRate > 0.0 -> minOf(goldNisabValue, silverNisabValue)
            gRate > 0.0 -> goldNisabValue
            sRate > 0.0 -> silverNisabValue
            else -> Double.MAX_VALUE
        }

        // 5. Determine Eligibility & Zakat
        val eligible = netWorth >= nisabThreshold && netWorth > 0
        val zakat = if (eligible) netWorth * ZAKAT_PERCENTAGE else 0.0

        _uiState.update {
            it.copy(
                totalNetWorth = netWorth,
                nisabThreshold = nisabThreshold,
                goldNisabValue = goldNisabValue,
                silverNisabValue = silverNisabValue,
                isEligible = eligible,
                zakatPayable = zakat,
                calculated = true,
                validationError = null
            )
        }
    }

    fun reset() {
        _uiState.value = ZakatState()
    }
}