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
    val otherInvestments: String = "", // Shares, merchandise, money owed to you

    // Liabilities
    val liabilities: String = "", // Debts, immediate expenses

    // Results
    val totalNetWorth: Double = 0.0,
    val nisabThreshold: Double = 0.0,
    val zakatPayable: Double = 0.0,
    val isEligible: Boolean = false,
    val calculated: Boolean = false
)

class ZakathViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ZakatState())
    val uiState: StateFlow<ZakatState> = _uiState.asStateFlow()

    // Update functions for UI inputs
    fun updateGoldRate(value: String) = _uiState.update { it.copy(goldRatePerGram = value, calculated = false) }
    fun updateSilverRate(value: String) = _uiState.update { it.copy(silverRatePerGram = value, calculated = false) }
    fun updateGoldWeight(value: String) = _uiState.update { it.copy(goldWeightGrams = value, calculated = false) }
    fun updateSilverWeight(value: String) = _uiState.update { it.copy(silverWeightGrams = value, calculated = false) }
    fun updateCash(value: String) = _uiState.update { it.copy(cashInHandBank = value, calculated = false) }
    fun updateInvestments(value: String) = _uiState.update { it.copy(otherInvestments = value, calculated = false) }
    fun updateLiabilities(value: String) = _uiState.update { it.copy(liabilities = value, calculated = false) }

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

        // 1. Calculate Asset Values
        val goldValue = gWeight * gRate
        val silverValue = sWeight * sRate

        // 2. Calculate Total Net Worth
        val totalAssets = goldValue + silverValue + cash + investments
        val netWorth = totalAssets - debts

        // 3. Calculate Nisab Threshold (Using Silver as the standard for mixed wealth)
        // Silver Nisab is approx 612.36 grams
        val silverNisabGrams = 612.36
        val nisabValue = silverNisabGrams * sRate

        // 4. Determine Eligibility & Zakat
        val eligible = netWorth >= nisabValue && netWorth > 0
        val zakat = if (eligible) netWorth * 0.025 else 0.0

        _uiState.update {
            it.copy(
                totalNetWorth = netWorth,
                nisabThreshold = nisabValue,
                isEligible = eligible,
                zakatPayable = zakat,
                calculated = true
            )
        }
    }

    fun reset() {
        _uiState.value = ZakatState()
    }
}